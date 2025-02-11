error id: local1101
file://<WORKSPACE>/examples/spark-cobol-s3-standalone/src/main/scala/com/example/spark/cobol/app/LocalBinaryFileReader.scala
empty definition using pc, found symbol in pc: 
empty definition using semanticdb
|empty definition using fallback
non-local guesses:
	 -readerParams/minimumRecordLength.
	 -readerParams/minimumRecordLength#
	 -readerParams/minimumRecordLength().
	 -scala/Predef.readerParams.minimumRecordLength.
	 -scala/Predef.readerParams.minimumRecordLength#
	 -scala/Predef.readerParams.minimumRecordLength().

Document text:

```scala
import za.co.absa.cobrix.cobol.parser.CopybookParser
import za.co.absa.cobrix.cobol.reader.extractors.record.RecordExtractors
import java.nio.file.{Files, Paths}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.expressions.GenericRow
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import za.co.absa.cobrix.cobol.reader.extractors.record.RecordExtractors
import za.co.absa.cobrix.cobol.reader.parameters.{ReaderParameters, CobolParameters}
import za.co.absa.cobrix.cobol.reader.schema.{CobolSchema => CobolReaderSchema}
import za.co.absa.cobrix.spark.cobol.reader.RowHandler
import za.co.absa.cobrix.spark.cobol.schema.CobolSchema

object LocalBinaryFileReader {
  def main(args: Array[String]): Unit = {
    // Define copybook
    val copybookContent ="""
      01  COMPANY-DETAILS.
        05  SEGMENT-ID           PIC X(5).
        05  COMPANY-ID           PIC X(10).
        05  STATIC-DETAILS.
           10  COMPANY-NAME      PIC X(15).
           10  ADDRESS           PIC X(25).
    """

    // Parse copybook
    val ast = CopybookParser.parseTree(copybookContent)
    
    // Read binary file
    val binaryData = Files.readAllBytes(Paths.get("src/test/resources/data.bin"))
    
    // Extract record
    // val record = RecordExtractors.extractRecord(
    //   binaryData,
    //   0,           // offset
    //   binaryData.length,  // record length
    //   ast
    // )
    // create a Reder Params object by using the ReaderParameters class
    val readerParams = ReaderParameters(
                        encoding = EBCDIC,
                        // File properties
                        isRecordSequence = false,
                        recordLength = Some(100),  // Adjust based on your record size
                        isIndexGenerationNeeded = true,
                        
                        // Record format
                        recordFormat = None,       // For fixed-length records
                        recordStartOffset = 0,
                        recordEndOffset = 0,
                        
                        // ASCII/EBCDIC conversion
                        asciiCharset = "US-ASCII",
                        ebcdicCodePage = "common",
                        
                        // Debugging
                        generateRecordId = true,
                        strictSignHandling = false,
                        
                        // Performance
                        inputSplitRecords = 50000,
                        inputSplitSizeMB = 100
                        )

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

def extractRecords[T](
    ast: CopybookAST,
    data: Array[Byte],
    offsetBytes: Int = 0,
    policy: SchemaRetentionPolicy = SchemaRetentionPolicy.KeepOriginal,
    variableLengthOccurs: Boolean = false,
    generateRecordId: Boolean = false,
    generateRecordBytes: Boolean = false,
    segmentLevelIds: List[String] = Nil,
    fileId: Int = 0,
    recordId: Long = 0,
    activeSegmentRedefine: String = "",
    generateInputFileField: Boolean = false,
    inputFileName: String = "",
    handler: RecordHandler[T]
  ): Seq[GenericRow] = {
    
    val recordHandler = new RowHandler()
    
    // Use the provided ast parameter
    RecordExtractors.extractRecord[GenericRow](
      ast,
      data,
      offsetBytes,
      data.length,
      recordHandler
    )
    
    // Return extracted records
    Seq(record)
  }

}

```

#### Short summary: 

empty definition using pc, found symbol in pc: 