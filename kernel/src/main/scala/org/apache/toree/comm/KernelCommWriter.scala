/*
 * Copyright 2014 IBM Corp.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.toree.comm

import org.apache.toree.annotations.Experimental
import org.apache.toree.kernel.protocol.v5
import org.apache.toree.kernel.protocol.v5.content.{CommMsg, CommOpen, CommClose, CommContent}
import org.apache.toree.kernel.protocol.v5._
import org.apache.toree.kernel.protocol.v5.kernel.ActorLoader

/**
 * Represents a CommWriter to send messages from the Kernel to the Client.
 *
 * @param actorLoader The actor loader to use for loading actors responsible for
 *                    communication
 * @param kmBuilder The kernel message builder used to construct kernel messages
 * @param commId The comm id associated with this writer (defaults to a
 *               random UUID)
 */
@Experimental
class KernelCommWriter(
  private val actorLoader: ActorLoader,
  private val kmBuilder: KMBuilder,
  override private[comm] val commId: v5.UUID
)  extends CommWriter(commId) {
  /**
   * Sends the comm message (open/msg/close) to the actor responsible for
   * relaying messages.
   *
   * @param commContent The message to relay (will be packaged)
   *
   * @tparam T Either CommOpen, CommMsg, or CommClose
   */
  override protected[comm] def sendCommKernelMessage[
    T <: KernelMessageContent with CommContent
  ](commContent: T): Unit = {
    val messageType = commContent match {
      case _: CommOpen  => CommOpen.toTypeString
      case _: CommMsg   => CommMsg.toTypeString
      case _: CommClose => CommClose.toTypeString
      case _            => throw new Throwable("Invalid kernel message type!")
    }
    actorLoader.load(SystemActorType.KernelMessageRelay) !
      kmBuilder.withHeader(messageType).withContentString(commContent).build
  }
}
