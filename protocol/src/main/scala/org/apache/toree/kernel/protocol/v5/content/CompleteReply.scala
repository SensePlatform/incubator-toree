/*
 * Copyright 2014 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.toree.kernel.protocol.v5.content

import org.apache.toree.kernel.protocol.v5.{KernelMessageContent, Metadata}
import play.api.libs.json.Json

case class CompleteReply (
  matches: List[String],
  cursor_start: Int,
  cursor_end: Int,
  metadata: Metadata,
  status: String,
  ename: Option[String],
  evalue: Option[String],
  traceback: Option[List[String]]
) extends KernelMessageContent {
  override def content : String =
    Json.toJson(this)(CompleteReply.completeReplyWrites).toString
}

object CompleteReply extends TypeString {
  implicit val completeReplyReads = Json.reads[CompleteReply]
  implicit val completeReplyWrites = Json.writes[CompleteReply]

  /**
   * Returns the type string associated with this object.
   *
   * @return The type as a string
   */
  override def toTypeString: String = "complete_reply"
}
