/*
 * Copyright (C) 2011 lightcouch.org
 * Copyright (c) 2015 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.cloudant.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DbInfo;
import com.cloudant.test.main.RequiresDB;
import com.cloudant.tests.util.CloudantClientResource;
import com.cloudant.tests.util.DatabaseResource;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.RuleChain;

import java.util.List;

@Category(RequiresDB.class)
public class DBServerTest {

    public static CloudantClientResource clientResource = new CloudantClientResource();
    public static DatabaseResource dbResource = new DatabaseResource(clientResource);
    @ClassRule
    public static RuleChain chain = RuleChain.outerRule(clientResource).around(dbResource);

    private static CloudantClient account;
    private static Database db;

    @BeforeClass
    public static void setUp() {
        account = clientResource.get();
        db = dbResource.get();
    }


    @Test
    public void dbInfo() {
        DbInfo dbInfo = db.info();
        assertNotNull(dbInfo);
    }

    @Test
    public void serverVersion() {
        String version = account.serverVersion();
        assertNotNull(version);
    }

    @Test
    public void allDBs() {
        List<String> allDbs = account.getAllDbs();
        assertThat(allDbs.size(), is(not(0)));
    }

    @Test
    public void ensureFullCommit() {
        db.ensureFullCommit();
    }

    @Test
    public void uuids() {
        List<String> uuids = account.uuids(10);
        assertThat(uuids.size(), is(10));
    }
}
