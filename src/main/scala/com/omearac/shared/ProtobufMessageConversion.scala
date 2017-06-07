package com.omearac.shared

import akka.util.Timeout
import com.omearac.kafka_messages.{ExampleAppEvent, KafkaMessage}
import com.omearac.shared.EventMessages.FailedMessageConversion
import scala.concurrent.duration._


/**
  * Here we define a typeclass which converts case class messages to/from Protobuff byte arrays.
  * Currently, we can convert KafkaMessage and ExampleAppEvent messages byte arrays.
  * Any additional case class types need to have conversion methods defined here.
  */


object ProtobufMessageConversion {
    implicit val resolveTimeout = Timeout(3 seconds)

    trait Conversion[T] {
        def convertFromByteArray(msg: Array[Byte]): Either[FailedMessageConversion, T]
        def convertToByteArray(msg: T): Array[Byte]
    }

    //Here is where we create implicit objects for each Message Type you wish to convert to/from JSON
    object Conversion {

        implicit object KafkaMessageConversions extends Conversion[KafkaMessage]  {

            /**
              * Converts the message body retrieved from Kafka (a Byte Array from Protobuf) to KafkaMessage case class
              * @param msg is the binary message to be converted to KafkaMessage case class
              * @return either a KafkaMessage or Unit (if conversion fails)
              */
            def convertFromByteArray(msg: Array[Byte]): Either[FailedMessageConversion, KafkaMessage] = {
                try {
                    Right(KafkaMessage.parseFrom(msg))
                }
                catch {
                    case e: Exception => Left(FailedMessageConversion("kafkaTopic", msg.toString, "to: KafkaMessage"))
                }
            }
            def convertToByteArray(msg: KafkaMessage) = {
                msg.toByteArray
            }
        }

        implicit object ExampleAppEventConversion extends Conversion[ExampleAppEvent] {

            /**
              * Converts the message body retrieved from Kafka (a Byte Array from Protobuf) to ExampleAppEvent case class
              * @param msg is the binary message to be converted to ExampleAppEvent case class
              * @return either a ExampleAppEvent or Unit (if conversion fails)
              */
            def convertFromByteArray(msg: Array[Byte]): Either[FailedMessageConversion, ExampleAppEvent] = {
                try {
                    Right(ExampleAppEvent.parseFrom(msg))
                }
                catch {
                    case e: Exception => Left(FailedMessageConversion("kafkaTopic", msg.toString, "to: ExampleAppEvent"))
                }
            }
            def convertToByteArray(msg: ExampleAppEvent) = {
                msg.toByteArray
            }
        }

        //Adding some sweet sweet syntactic sugar
        def apply[T: Conversion] : Conversion[T] = implicitly
    }
}


