error id: org/apache/spark/sql/catalyst/expressions/GenericRow#
file://<WORKSPACE>/spark-cobol/src/main/scala/za/co/absa/cobrix/spark/cobol/reader/SparkCobolRowType.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol org/apache/spark/sql/catalyst/expressions/GenericRow#
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
/*
 * Copyright 2018 ABSA Group Limited
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

package za.co.absa.cobrix.spark.cobol.reader

import org.apache.spark.sql.catalyst.expressions.GenericRow
import za.co.absa.cobrix.cobol.parser.ast.Group
import za.co.absa.cobrix.cobol.reader.extractors.record.RecordHandler


class RowHandler extends RecordHandler[GenericRow] with Serializable {
  override def create(values: Array[Any], group: Group): GenericRow = new GenericRow(values)

  override def toSeq(record: GenericRow): Seq[Any] = record.toSeq

  override def foreach(record: GenericRow)(f: Any => Unit): Unit = {
    val len = record.length
    var i = 0
    while (i < len) {
      f(record(i))
      i += 1
    }
  }
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: 