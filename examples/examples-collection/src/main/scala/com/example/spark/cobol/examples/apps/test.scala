
package com.example.spark.cobol.examples.apps

import org.apache.spark.sql.{SaveMode, SparkSession}

// This is an example Spark Job that uses COBOL data source.
// IMPORTANT! To run this locally change the scope of all Scala and Spark libraries from 'provided' to 'compile' in pom.xml
//            But revert it to 'provided' to create an uber jar for running on a cluster
object CobolSparkExample10 {

  def main(args: Array[String]): Unit = {

    val sparkBuilder = SparkSession.builder().appName("Cobol source reader example 1")
    val spark = sparkBuilder
      .master("local[*]")
      .getOrCreate()

    // This is an example read from a mainframe data file.
    // You can turn on/off the 'generate_record_id' and 'schema_retention_policy' options to see what difference it makes.
    val df = spark
      .read
      .format("cobol")
      .option("copybook", "../../examples/example_data/raw_file.cob")
      //.option("generate_record_id", true)                   // Generates File_Id and Record_Id fields for line order dependent data
      //.option("schema_retention_policy", "collapse_root")   // Collapses the root group returning it's field on the top level of the schema
      .load("examples/example_data/raw_data")

    // If you get this exception:
    //   Class Not found exception java.lang.ClassNotFoundException: Failed to find data source: cobol.
    // please use full class name of the data source:
    //     .format("za.co.absa.cobrix.spark.cobol.source")

    df.printSchema
    println(df.count)
    df.show(100, truncate = false)

    df.toJSON.take(100).foreach(println)

    df.write.mode(SaveMode.Overwrite)
      .parquet("data/output/example")
  }

}
