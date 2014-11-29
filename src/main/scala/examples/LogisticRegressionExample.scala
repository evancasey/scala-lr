package examples

import java.util.Random

import breeze.linalg.DenseVector
import models.{LogisticRegression, DataPoint}

object LogisticRegressionExample {

  type TrainData = Seq[DataPoint[DenseVector[Double],Double]]
  type TestData = Seq[DenseVector[Double]]

  val N = 10000  // Number of data points
  val D = 10   // Number of dimensions
  val R = 0.7 // Scaling factor
  val LR = 1.0 // Learning rate
  val ITERATIONS = 1000
  val rand = new Random(42)

  def generateTrainAndTestData: (TrainData,TestData) = {
    def generatePoint(i: Int): DataPoint[DenseVector[Double], Double] = {
      val y = if(i % 2 == 0) -1.0 else 1.0
      val x = DenseVector.fill(D){rand.nextGaussian + y * R}
      DataPoint(x,y)
    }

    val train = Seq.tabulate(9 * N / 10)(generatePoint)
    val test = Seq.tabulate(N / 10)(generatePoint).map(_.featureVector)

    (train,test)
  }

  def main(args: Array[String]) = {

    val (trainData,testData) = generateTrainAndTestData
    val lr = LogisticRegression(ITERATIONS, LR)
    val lrModel = lr.train(trainData)
    val results = lrModel.predict(testData)
    println(results)
  }

}
