/**
 * Cerberus Copyright (C) 2013 - 2017 cerberustesting
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cerberus.engine.scheduledtasks;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.cerberus.crud.service.IParameterService;
import org.cerberus.crud.service.ITestCaseExecutionQueueService;
import org.cerberus.engine.queuemanagement.IExecutionThreadPoolService;
import org.cerberus.exception.CerberusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author bcivel
 */
// Component = instancié au déploiement. Singleton. 1 par instance
@Component
public class ScheduledTaskRunner {

    @Autowired
    private IParameterService parameterService;
    @Autowired
    private ITestCaseExecutionQueueService testCaseExecutionQueueService;
    @Autowired
    private IExecutionThreadPoolService executionThreadPoolService;

    private int b1TickNumberTarget = 5;
    private int b1TickNumber = 0;
    private final int b2TickNumberTarget = 30;
    private int b2TickNumber = 0;

    private static final org.apache.logging.log4j.Logger LOG = org.apache.logging.log4j.LogManager.getLogger(ScheduledTaskRunner.class);

    @Scheduled(fixedDelay = 1000 /* 1000 */)
    public void nextStep() {
        if (b1TickNumber < b1TickNumberTarget) {
            b1TickNumber++;
        } else {
            b1TickNumber = 0;
            // We get the new period from paarameter and trigger the Queue automatic cancellation job.
            b1TickNumberTarget = parameterService.getParameterIntegerByKey("cerberus_automaticqueuecancellationjob_period", "", 10);
            performBatch1_CancelOldQueueEntries();
        }

        if (b2TickNumber < b2TickNumberTarget) {
            b2TickNumber++;
        } else {
            b2TickNumber = 0;
            // We trigger the Queue Processing job.
            performBatch2_ProcessQueue();
        }

    }

    private void performBatch1_CancelOldQueueEntries() {
        LOG.debug("automaticqueuecancellationjob Sheduled Task triggered.");
        testCaseExecutionQueueService.cancelRunningOldQueueEntries();
//        LOG.debug("automaticqueuecancellationjob Task ended.");
    }

    private void performBatch2_ProcessQueue() {
        LOG.debug("Queue_Processing_Job Sheduled Task triggered.");
        try {
            executionThreadPoolService.executeNextInQueue(false);
        } catch (CerberusException ex) {
            Logger.getLogger(ScheduledTaskRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
//        LOG.debug("Queue_Processing_Job Task ended.");
    }

}