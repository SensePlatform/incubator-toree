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

package org.apache.toree

package object magic {
  /**
   * Represents the output of a magic execution.
   */
  // TODO: This is a duplicate of Data in kernel protocol, needs to be given
  //       a type/val that can be translated into a specific protocol via
  //       implicits - or some other transformation - to separate this from
  //       the protocol type
  type CellMagicOutput = Map[String, String]
  val CellMagicOutput = Map
  
  type LineMagicOutput = Unit
  val LineMagicOutput : LineMagicOutput = ()
}
