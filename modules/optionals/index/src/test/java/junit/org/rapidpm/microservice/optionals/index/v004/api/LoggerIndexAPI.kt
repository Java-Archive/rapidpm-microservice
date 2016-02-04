/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package junit.org.rapidpm.microservice.optionals.index.v004.api

/**
 *  Copyright (C) 2015 RapidPM
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * Created by RapidPM - Team on 01.02.16.
 */


//data class LoggerEvent(val level: String, val message: String, val timestamp: LocalDateTime): Serializable;

fun toLuceneQuery(logEvent: LoggerEvent): String {
  val level = logEvent.level
  val message = logEvent.message
  val timestamp = logEvent.timestamp

  return IndexOfTypeLoggerEvent.LEVEL + ":" + "\"" + level + "\"" + " AND " +
      IndexOfTypeLoggerEvent.MESSAGE + ":" + "\"" + message + "\"" + " AND " +
      IndexOfTypeLoggerEvent.TIMESTAMP + ":" + "\"" + timestamp + "\"";
}