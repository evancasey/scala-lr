package models

import breeze.linalg.DenseVector
import org.scalatest._
import Matchers._

class LogisticRegressionSpec extends WordSpec {

  "LogisticRegression" should {
    "generate a correct prediction from training data" in {

      val lr = LogisticRegression(100000, 100.0)

      val trainData: Seq[DataPoint] = Seq(
        DataPoint(DenseVector(-1, 0), 0),
        DataPoint(DenseVector(0, 1), 1),
        DataPoint(DenseVector(1, 1), 1)
      )

      val testData: Seq[DenseVector[Double]] = Seq(
        DenseVector(-1, 0),
        DenseVector(0, 1),
        DenseVector(1, 1)
      )

      val lrModel = lr.train(trainData)

      val predictedLabels = lrModel.predict(testData)
      val trainLabels = trainData.map(_.label)

      predictedLabels.zip(trainLabels).foreach { case (predicted, train) =>
        predicted shouldEqual train +- .0001
      }
    }
  }

}
