error id: local2260
file://<WORKSPACE>/examples/spark-cobol-s3-standalone/src/main/scala/com/example/spark/cobol/app/SparkCobolS3App.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol local2260
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
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.spark.cobol.app
// import utils.{ConfigUtils, SparkS3Utils}
import com.example.spark.cobol.app.utils.{ConfigUtils, SparkS3Utils}
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession
import za.co.absa.cobrix.spark.cobol.utils.SparkUtils
//put the parser here.
// import za.co.absa.cobol.cobol-parser.CopybookParser;

/**
  * This is an example Spark/Cobol Application reading data from S3 as a standalone application.
  *
  * You can run it locally using an IDE.
  *
  * To generate the uber jar use
  * `mvn package`
  *
  * To run the application use
  * {{{
  *   java -cp java -cp spark-cobol-s3-sa-0.0.1-SNAPSHOT.jar com.example.spark.cobol.app.SparkCobolS3App
  * }}}
  */
object SparkCobolS3App {
  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = buildLocalSparkSession()

    runMySparkApp()
  }

  /**
    * This is the actual processing of a mainframe file located in an S3 bucket.
    */
  def runMySparkApp()(implicit spark: SparkSession): Unit = {
    // This is the copybook for the data file.
    // Alternatively, you can,
    // - put the file locally and use "file:///path/to/copybook"
    // - put the file to S3 and use "s3a://bucket/path/to/copybook"
    val copybook0 =
    """        01  RECORD.
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

    val copybook ="""
              01  COMPANY-DETAILS.
            05  SEGMENT-ID           PIC X(5).
            05  COMPANY-ID           PIC X(10).
            05  STATIC-DETAILS.
               10  COMPANY-NAME      PIC X(15).
               10  ADDRESS           PIC X(25).
               10  TAXPAYER.
                  15  TAXPAYER-TYPE  PIC X(1).
                  15  TAXPAYER-STR   PIC X(8).
                  15  TAXPAYER-NUM  REDEFINES TAXPAYER-STR
                                     PIC 9(8) COMP.
            05  CONTACTS REDEFINES STATIC-DETAILS.
               10  PHONE-NUMBER      PIC X(17).
               10  CONTACT-PERSON    PIC X(28).
    """.stripMargin

    val copybookPath = "<WORKSPACE>/examples/example_data/raw_file.cob"
    val inputData = "<WORKSPACE>/examples/example_data/raw_data/"

    val df = spark.read
      .format("cobol")
      // .option("copybook_contents", copybook)
      .option("copybook", copybookPath)
      .option("schema_retention_policy", "collapse_root")
      .load(inputData)
      // .load("s3a://mybucket/mydata")

    df.printSchema()

    df.show

    val json = SparkUtils.prettyJSON(df.toJSON.take(3).mkString("[", ",", "]"))
    println(json)
  }

  /**
    * This is a generic way to create a Spark session in local master mode.
    * (The Spark master is defined in the configuration, see `reference.conf`)
    */
  def buildLocalSparkSession(): SparkSession = {
    val conf = ConfigFactory.load()

    // See 'spark.conf.option' in 'reference.conf' for Spark configuration
    val sparkSessionBuilder = SparkSession
      .builder()
      .appName("Spark Cobol S3 example app")

    // Apply extra Spark configuration from 'reference.conf' and 'application.conf'
    val extraOptions = ConfigUtils.getExtraOptions(conf, "spark.conf.option")
    ConfigUtils.logExtraOptions("Extra Spark Config:", extraOptions)
    val sparkSessionBuilderWithExtraOptApplied = extraOptions.foldLeft(sparkSessionBuilder) {
      case (builder, (key, value)) => builder.config(key, value)
    }

    // Create a Spark session
    val spark = sparkSessionBuilderWithExtraOptApplied.getOrCreate()

    // Apply Hadoop configuration to enable S3 access
    SparkS3Utils.enableSparkS3FromConfig(spark, conf)

    spark
  }
}

```

#### Short summary: 

empty definition using pc, found symbol in pc: 