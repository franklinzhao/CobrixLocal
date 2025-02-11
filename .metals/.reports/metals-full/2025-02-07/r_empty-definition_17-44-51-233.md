error id: local347
file://<WORKSPACE>/examples/spark-cobol-s3-standalone/src/main/scala/com/example/spark/cobol/app/LocalBinaryFileReader.scala
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol local347
|empty definition using fallback
non-local guesses:
	 -

Document text:

```scala
import za.co.absa.cobrix.cobol.parser.CopybookParser
import za.co.absa.cobrix.cobol.reader.extractors.record.RecordExtractors
import java.nio.file.{Files, Paths}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.expressions.GenericRow
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import za.co.absa.cobrix.cobol.reader.extractors.record.RecordExtractors
import za.co.absa.cobrix.cobol.reader.parameters.ReaderParameters
import za.co.absa.cobrix.cobol.reader.schema.{CobolSchema => CobolReaderSchema}
import za.co.absa.cobrix.spark.cobol.reader.RowHandler
import za.co.absa.cobrix.spark.cobol.schema.CobolSchema

object LocalBinaryFileReader {
  def main(args: Array[String]): Unit = {
    // Define copybook
    val copybook ="""
      01  COMPANY-DETAILS.
        05  SEGMENT-ID           PIC X(5).
        05  COMPANY-ID           PIC X(10).
        05  STATIC-DETAILS.
           10  COMPANY-NAME      PIC X(15).
           10  ADDRESS           PIC X(25).
    """

    // Parse copybook
    val ast = CopybookParser.parseTree(copybook)
    
    // Read binary file
    val binaryData = Files.readAllBytes(Paths.get("src/test/resources/data.bin"))
    
    // Extract record
    // val record = RecordExtractors.extractRecord(
    //   binaryData,
    //   0,           // offset
    //   binaryData.length,  // record length
    //   ast
    // )

    val cobolSchema = CobolSchema.fromBaseReader(CobolReaderSchema.fromReaderParameters(Seq(copybookContent), readerParams))
    // val sparkSchema = cobolSchema.getSparkSchema

    val recordHandler = new RowHandler()

    val schemaRetentionPolicy = readerParams.schemaPolicy

    val minimumRecordLength = readerParams.minimumRecordLength
    val maximumRecordLength = readerParams.maximumRecordLength

    record = RecordExtractors.extractRecord[GenericRow](cobolSchema.getCobolSchema.ast,
                                                                binaryData,
                                                                0,
                                                                schemaRetentionPolicy,
                                                                generateRecordBytes = readerParams.generateRecordBytes,
                                                                handler = recordHandler)

    // Print extracted fields
    record.getFieldValue("SEGMENT-ID").foreach(println)
    record.getFieldValue("COMPANY-ID").foreach(println)
    record.getFieldValue("COMPANY-NAME").foreach(println)
    record.getFieldValue("ADDRESS").foreach(println)
  }

}

```

#### Short summary: 

empty definition using pc, found symbol in pc: 