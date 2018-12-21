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
package org.cerberus.crud.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cerberus.crud.entity.TestCaseExecution;
import org.cerberus.crud.entity.TestCaseExecutionQueueDep;
import org.cerberus.exception.CerberusException;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerList;

/**
 * Interface that defines the public methods to manage Application data on table
 * Insert, Delete, Update, Find
 *
 * @author tbernardes
 * @version 1.0, 15/10/13
 * @since 0.9.0
 */
public interface ITestCaseExecutionQueueDepDAO {

    /**
     *
     * @param id
     * @return
     */
    AnswerItem<TestCaseExecutionQueueDep> readByKey(long id);

    /**
     *
     * @param exeId
     * @return
     */
    AnswerList<TestCaseExecutionQueueDep> readByExeId(long exeId);

    /**
     *
     * @param exeQueueId
     * @return
     */
    AnswerItem<Integer> readNbWaitingByExeQueue(long exeQueueId);

    /**
     *
     * @param exeQueueId
     * @return
     */
    AnswerItem<Integer> readNbReleasedWithNOKByExeQueue(long exeQueueId);

    /**
     *
     * @param exeId
     * @return
     */
    AnswerList<Long> readExeQueueIdByExeId(long exeId);

    /**
     *
     * @param startPosition
     * @param length
     * @param columnName
     * @param sort
     * @param searchParameter
     * @param individualSearch
     * @return
     */
    AnswerList<TestCaseExecutionQueueDep> readByCriteria(int startPosition, int length, String columnName, String sort, String searchParameter, Map<String, List<String>> individualSearch);

    /**
     *
     * @param queueId
     * @param env
     * @param country
     * @param tag
     * @param test
     * @param testcase
     * @return
     */
    AnswerItem<Integer> insertFromTCDep(long queueId, String env, String country, String tag, String test, String testcase);

    /**
     *
     * @param queueId
     * @param fromQueueId
     * @return
     */
    AnswerItem<Integer> insertFromQueueExeDep(long queueId, long fromQueueId);

    /**
     *
     * @param env
     * @param Country
     * @param tag
     * @param type
     * @param test
     * @param testCase
     * @param comment
     * @param exeId
     * @return
     */
    AnswerItem<Integer> updateStatusToRelease(String env, String Country, String tag, String type, String test, String testCase, String comment, long exeId);

    /**
     *
     * @param searchParameter
     * @param individualSearch
     * @param columnName
     * @return
     */
    AnswerList<String> readDistinctValuesByCriteria(String searchParameter, Map<String, List<String>> individualSearch, String columnName);

    HashMap<TestCaseExecution,List<TestCaseExecutionQueueDep>> readDependenciesByTestCaseExecution(List<TestCaseExecution> testCaseExecutions) throws CerberusException;
}
