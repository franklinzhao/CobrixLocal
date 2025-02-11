error id: local331
file://<WORKSPACE>/examples/spark-cobol-s3-standalone/src/main/scala/com/example/spark/cobol/app/RowExtractorTest.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
found definition using fallback; symbol Copybook
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

package com.example.spark.cobol.app

// import org.scalatest.funsuite.AnyFunSuite
import za.co.absa.cobrix.cobol.parser.{Copybook, CopybookParser}
import za.co.absa.cobrix.cobol.reader.extractors.record.RecordExtractors


object RowExtractorTest{
  val copybookPath = "<WORKSPACE>/examples/example_data/raw_file.cob"
  val inputData = "<WORKSPACE>/examples/example_data/raw_data/"

  val copyBookContents: String =
    """       01  RECORD.
           05  ID                        PIC S9(4)  COMP.
           05  COMPANY.
               10  SHORT-NAME            PIC X(10).
               10  COMPANY-ID-NUM        PIC 9(5) COMP-3.
               10  COMPANY-ID-STR
			         REDEFINES  COMPANY-ID-NUM PIC X(3).
           05  METADATA.
               10  CLIENTID              PIC X(15).
               10  REGISTRATION-NUM      PIC X(10).
               10  NUMBER-OF-ACCTS       PIC 9(03) COMP-3.
               10  ACCOUNT.
                   12  ACCOUNT-DETAIL    OCCURS 80
                                         DEPENDING ON NUMBER-OF-ACCTS.
                      15  ACCOUNT-NUMBER     PIC X(24).
                      15  ACCOUNT-TYPE-N     PIC 9(5) COMP-3.
                      15  ACCOUNT-TYPE-X     REDEFINES
                           ACCOUNT-TYPE-N  PIC X(3).

""".stripMargin

  val copybook: Copybook = CopybookParser.parseTree(copyBookContents)
  //define a method to return a copybook with input of copybook file path
  def getCopybook(copybookPath: String): Copybook = {
      import java.io.File
      import scala.io.Source
      
      try {
        val source = Source.fromFile(new File(copybookPath))
        val copybookContent = try {
          source.mkString
        } finally {
          source.close()
        }
        CopybookParser.parseTree(copybookContent)
      } catch {
        case ex: Exception =>
          println(s"Error reading copybook file: ${ex.getMessage}")
          throw ex
      }
    }
  
  println("copybook1 generateRecordLayoutPositions():::"+getCopybook(copybookPath).generateRecordLayoutPositions())

  println("copybook1 record Length:::"+getCopybook(copybookPath).getRecordSize)

  val bytes: Array[Byte]  = Array[Byte](
    0x00.toByte, 0x06.toByte, 0xC5.toByte, 0xE7.toByte, 0xC1.toByte, 0xD4.toByte, 0xD7.toByte, 0xD3.toByte,
    0xC5.toByte, 0xF4.toByte, 0x40.toByte, 0x40.toByte, 0x00.toByte, 0x00.toByte, 0x0F.toByte, 0x40.toByte,
    0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte,
    0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte,
    0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte, 0x40.toByte,
    0x00.toByte, 0x3F.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte,
    0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte,
    0xF2.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF4.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte,
    0xF1.toByte, 0xF2.toByte, 0x00.toByte, 0x00.toByte, 0x0F.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte,
    0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte,
    0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF3.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF4.toByte,
    0xF0.toByte, 0xF0.toByte, 0xF1.toByte, 0xF0.toByte, 0xF2.toByte, 0x00.toByte, 0x00.toByte, 0x1F.toByte,
    0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte,
    0xF5.toByte, 0xF0.toByte, 0xF0.toByte, 0xF6.toByte, 0xF0.toByte, 0xF0.toByte, 0xF1.toByte, 0xF2.toByte,
    0xF0.toByte, 0xF0.toByte, 0xF3.toByte, 0xF0.toByte, 0xF1.toByte, 0xF0.toByte, 0xF0.toByte, 0xF0.toByte,
    0x00.toByte, 0x00.toByte, 0x2F.toByte
  )
  //define a method to return a Array[Byte] with input of data file path
  def getBinaryData(dataPath: String): Array[Byte] = {
  import java.nio.file.{Files, Paths}
  
  try {
    Files.readAllBytes(Paths.get(dataPath))
  } catch {
    case ex: Exception =>
      println(s"Error reading binary file: ${ex.getMessage}")
      throw ex
  }
}

  val startOffset: Int = 0

  def main(args: Array[String]): Unit = {
    val row = RecordExtractors.extractRecord(copybook.ast, bytes, startOffset, handler = new SimpleRecordHandler())
    // [[6,[EXAMPLE4,0,],[,,3,[Vector([000000000000002000400012,0,], [000000000000003000400102,1,], [000000005006001200301000,2,])]]]]

    val innerRow = row.head.asInstanceOf[Array[Any]]

      println("copybook Copybook_ast:::"+copybook.ast)
      print("copybook schema:::"+copybook.getCobolSchema)

    // id
    println("\n row number: "+innerRow.head.asInstanceOf[Int]++"\n")

    // // short name
    println(innerRow(1).asInstanceOf[Array[Any]].head.asInstanceOf[String])
    //=== "EXAMPLE4")

    // // company id
    // assert(innerRow(1).asInstanceOf[Array[Any]](1).asInstanceOf[Int] === 0)

    // // number of accounts
    // assert(innerRow(2).asInstanceOf[Array[Any]](2).asInstanceOf[Int] === 3)

    // // account detail
    // val accounts: Array[Any] = innerRow(2).asInstanceOf[Array[Any]](3).asInstanceOf[Array[Any]](0).asInstanceOf[Array[Any]]
    // val account: Array[Any] = accounts(0).asInstanceOf[Array[Any]]

    // // account number
    // assert(account(0).asInstanceOf[String] === "000000000000002000400012")

    // //account type
    // assert(account(1).asInstanceOf[Int] === 0)
  }

import za.co.absa.cobrix.cobol.parser.ast.Group
import za.co.absa.cobrix.cobol.reader.extractors.record.RecordHandler

class SimpleRecordHandler extends RecordHandler[scala.Array[Any]] {
  override def create(values: Array[Any], group: Group): Array[Any] = values

  override def toSeq(record: Array[Any]): Seq[Any] = Seq[Any]()

  override def foreach(record: Array[Any])(f: Any => Unit): Unit = record.foreach(f)
}
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: 