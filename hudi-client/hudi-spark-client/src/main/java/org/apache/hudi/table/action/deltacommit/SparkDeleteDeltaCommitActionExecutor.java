/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hudi.table.action.deltacommit;

import org.apache.hudi.client.WriteStatus;
import org.apache.hudi.client.common.HoodieSparkEngineContext;
import org.apache.hudi.common.model.HoodieKey;
import org.apache.hudi.common.model.HoodieRecordPayload;
import org.apache.hudi.common.model.WriteOperationType;
import org.apache.hudi.config.HoodieWriteConfig;
import org.apache.hudi.table.HoodieTable;
import org.apache.hudi.table.action.HoodieWriteMetadata;
import org.apache.hudi.table.action.commit.SparkDeleteHelper;

import org.apache.spark.api.java.JavaRDD;

public class SparkDeleteDeltaCommitActionExecutor<T extends HoodieRecordPayload<T>>
    extends BaseSparkDeltaCommitActionExecutor<T> {

  private final JavaRDD<HoodieKey> keys;

  public SparkDeleteDeltaCommitActionExecutor(HoodieSparkEngineContext context,
                                              HoodieWriteConfig config, HoodieTable table,
                                              String instantTime, JavaRDD<HoodieKey> keys) {
    super(context, config, table, instantTime, WriteOperationType.DELETE);
    this.keys = keys;
  }

  @Override
  public HoodieWriteMetadata<JavaRDD<WriteStatus>> execute() {
    return SparkDeleteHelper.newInstance().execute(instantTime, keys, context, config, table, this);
  }
}
