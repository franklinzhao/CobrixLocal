// import za.co.absa.cobrix.cobol.parser.CopybookParser
// import za.co.absa.cobrix.cobol.reader.extractors.record.RecordExtractors
// import java.nio.file.{Files, Paths}
// import org.apache.spark.rdd.RDD
// import org.apache.spark.sql.catalyst.expressions.GenericRow
// import org.apache.spark.sql.{DataFrame, Row, SparkSession}
// import za.co.absa.cobrix.cobol.reader.extractors.record.RecordExtractors
// import za.co.absa.cobrix.cobol.reader.parameters.{ReaderParameters, CobolParameters}
// import za.co.absa.cobrix.cobol.reader.schema.{CobolSchema => CobolReaderSchema}
// import za.co.absa.cobrix.spark.cobol.reader.RowHandler
// import za.co.absa.cobrix.spark.cobol.schema.CobolSchema

// object LocalBinaryFileReader {
//   def main(args: Array[String]): Unit = {
//     // Define copybook
//     val copybookContent ="""
//       01  COMPANY-DETAILS.
//         05  SEGMENT-ID           PIC X(5).
//         05  COMPANY-ID           PIC X(10).
//         05  STATIC-DETAILS.
//            10  COMPANY-NAME      PIC X(15).
//            10  ADDRESS           PIC X(25).
//     """

//     // Parse copybook
//     val ast = CopybookParser.parseTree(copybookContent)
    
//     // Read binary file
//     val binaryData = Files.readAllBytes(Paths.get("src/test/resources/data.bin"))
    
//     // Extract record
//     // val record = RecordExtractors.extractRecord(
//     //   binaryData,
//     //   0,           // offset
//     //   binaryData.length,  // record length
//     //   ast
//     // )
//     // create a Reder Params object by using the ReaderParameters class
//     val readerParams = ReaderParameters(
//                              recordFormat = 'D',
//                              isEbcdic = true, //                Boolean = true,
//                              isText = true, //:                  Boolean = false,
//                              ebcdicCodePage = 'cp037',//:          String = "common",
//                              ebcdicCodePageClass = None, //:     Option[String] = None,
//                              asciiCharset = 'ISOS--XXXX', //:            Option[String] = None,
//                              fieldCodePage:           Map[String, String] = Map.empty[String, String],
//                              isUtf16BigEndian:        Boolean = true,
//                              floatingPointFormat:     FloatingPointFormat = FloatingPointFormat.IBM,
//                              variableSizeOccurs:      Boolean = false,
//                              recordLength:            Option[Int] = None,
//                              minimumRecordLength:     Int = 1,
//                              maximumRecordLength:     Int = Int.MaxValue,
//                              lengthFieldExpression:   Option[String] = None,
//                              lengthFieldMap:          Map[String, Int] = Map.empty,
//                              isRecordSequence:        Boolean = false,
//                              bdw:                     Option[Bdw] = None,
//                              isRdwBigEndian:          Boolean = false,
//                              isRdwPartRecLength:      Boolean = false,
//                              rdwAdjustment:           Int = 0,
//                              isIndexGenerationNeeded: Boolean = false,
//                              inputSplitRecords:       Option[Int] = None,
//                              inputSplitSizeMB:        Option[Int] = None,
//                              hdfsDefaultBlockSize:    Option[Int] = None,
//                              startOffset:             Int = 0,
//                              endOffset:               Int = 0,
//                              fileStartOffset:         Int = 0,
//                              fileEndOffset:           Int = 0,
//                              generateRecordId:        Boolean = false,
//                              generateRecordBytes:     Boolean = false,
//                              schemaPolicy:            SchemaRetentionPolicy = SchemaRetentionPolicy.CollapseRoot,
//                              stringTrimmingPolicy:    StringTrimmingPolicy = StringTrimmingPolicy.TrimBoth,
//                              allowPartialRecords:     Boolean = false,
//                              multisegment:            Option[MultisegmentParameters] = None,
//                              commentPolicy:           CommentPolicy = CommentPolicy(),
//                              strictSignOverpunch:     Boolean = true,
//                              improvedNullDetection:   Boolean = false,
//                              strictIntegralPrecision: Boolean = false,
//                              decodeBinaryAsHex:       Boolean = false,
//                              dropGroupFillers:        Boolean = false,
//                              dropValueFillers:        Boolean = true,
//                              fillerNamingPolicy:      FillerNamingPolicy = FillerNamingPolicy.SequenceNumbers,
//                              nonTerminals:            Seq[String] = Nil,
//                              occursMappings:          Map[String, Map[String, Int]] = Map(),
//                              debugFieldsPolicy:       DebugFieldsPolicy = DebugFieldsPolicy.NoDebug,
//                              recordHeaderParser:      Option[String] = None,
//                              recordExtractor:         Option[String] = None,
//                              rhpAdditionalInfo:       Option[String] = None,
//                              reAdditionalInfo:        String = "",
//                              inputFileNameColumn:     String = "",
//                              metadataPolicy:          MetadataPolicy = MetadataPolicy.Basic
//                            )

//                                 // Record format
//                         recordFormat = None,       // For fixed-length records
//                         encoding = EBCDIC,
//                         // File properties
//                         isRecordSequence = false,
//                         recordLength = Some(100),  // Adjust based on your record size
//                         isIndexGenerationNeeded = true,
//                         recordStartOffset = 0,
//                         recordEndOffset = 0,
//                         // ASCII/EBCDIC conversion
//                         asciiCharset = "US-ASCII",
//                         ebcdicCodePage = "common",
                        
//                         // Debugging
//                         generateRecordId = true,
//                         strictSignHandling = false,
                        
//                         // Performance
//                         inputSplitRecords = 50000,
//                         inputSplitSizeMB = 100
//                         )

//     val cobolSchema = CobolSchema.fromBaseReader(CobolReaderSchema.fromReaderParameters(Seq(copybookContent), readerParams))
//     // val sparkSchema = cobolSchema.getSparkSchema

//     val recordHandler = new RowHandler()

//     val schemaRetentionPolicy = readerParams.schemaPolicy

//     // val minimumRecordLength = readerParams.minimumRecordLength
//     // val maximumRecordLength = readerParams.maximumRecordLength

//     record = RecordExtractors.extractRecord[GenericRow](cobolSchema.getCobolSchema.ast,
//                                                                 binaryData,
//                                                                 0,
//                                                                 schemaRetentionPolicy,
//                                                                 generateRecordBytes = readerParams.generateRecordBytes,
//                                                                 handler = recordHandler)

//     // Print extracted fields
//     record.getFieldValue("SEGMENT-ID").foreach(println)
//     record.getFieldValue("COMPANY-ID").foreach(println)
//     record.getFieldValue("COMPANY-NAME").foreach(println)
//     record.getFieldValue("ADDRESS").foreach(println)
//   }

// def extractRecords[T](
//     ast: CopybookAST,
//     data: Array[Byte],
//     offsetBytes: Int = 0,
//     policy: SchemaRetentionPolicy = SchemaRetentionPolicy.KeepOriginal,
//     variableLengthOccurs: Boolean = false,
//     generateRecordId: Boolean = false,
//     generateRecordBytes: Boolean = false,
//     segmentLevelIds: List[String] = Nil,
//     fileId: Int = 0,
//     recordId: Long = 0,
//     activeSegmentRedefine: String = "",
//     generateInputFileField: Boolean = false,
//     inputFileName: String = "",
//     handler: RecordHandler[T]
//   ): Seq[GenericRow] = {
    
//     val recordHandler = new RowHandler()
    
//     // Use the provided ast parameter
//     RecordExtractors.extractRecord[GenericRow](
//       ast,
//       data,
//       offsetBytes,
//       data.length,
//       recordHandler
//     )
    
//     // Return extracted records
//     Seq(record)
//   }

// }
