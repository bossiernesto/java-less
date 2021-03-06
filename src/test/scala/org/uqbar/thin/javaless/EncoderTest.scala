package org.uqbar.thin.javaless

import scala.language.implicitConversions
import scala.collection.JavaConversions._
import org.scalatest.FreeSpec
import org.scalatest.Matchers
import org.scalatest.matchers.MatchResult
import org.scalatest.matchers.Matcher
import java.util.IdentityHashMap

class JavalessEncoderTest extends EncoderTest {
 
	"Javaless encode of" - {

		"classes" - {
			"should succeed" - {
				"for an empty class" in {
					val target = Class("MyClass", Nil)
					target should beEncodedTo("class MyClass { }", target -> (0 until 17))
				}
			}
		}

	}

}

//▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀
// ENCODER TEST
//▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄

abstract class EncoderTest extends FreeSpec with Matchers with Encoder {

	case class beEncodedTo(expectedText: String, expectedReferences: (SyntaxElement, Range)*) extends Matcher[SyntaxElement] {
		def apply(target: SyntaxElement) = {
			val result = encode(target) 
			val success = result match {
				case Success(`expectedText`, references) => references.size == expectedReferences.size && expectedReferences.forall{ case (key, value) => references.get(key) == value }
				case _ => false
			}

			MatchResult(
				success,
				s"Encoded $result did not match Success($expectedText, $expectedReferences)}",
				s"Encoded $result matched Success($expectedText, $expectedReferences)}"
			)
		}
	}
}