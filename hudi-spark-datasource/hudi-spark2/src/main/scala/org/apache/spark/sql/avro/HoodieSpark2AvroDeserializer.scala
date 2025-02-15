/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.avro

import org.apache.avro.Schema
import org.apache.spark.sql.types.DataType

/**
 * This is Spark 2 implementation for the [[HoodieAvroDeserializer]] leveraging [[PatchedAvroDeserializer]],
 * which is just copied over version of [[AvroDeserializer]] from Spark 2.4.4 w/ SPARK-30267 being back-ported to it
 */
class HoodieSpark2AvroDeserializer(rootAvroType: Schema, rootCatalystType: DataType)
  extends HoodieAvroDeserializer {

  private val avroDeserializer = new PatchedAvroDeserializer(rootAvroType, rootCatalystType)

  // As of Spark 3.1, this will return data wrapped with Option, so we make sure these interfaces
  // are aligned across Spark versions
  def deserialize(data: Any): Option[Any] = Some(avroDeserializer.deserialize(data))
}
