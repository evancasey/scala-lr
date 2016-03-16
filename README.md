scala-lr
========

scala-lr is a logistic regression library written in Scala.

Work in progress!

Example usage
-------------

```scala
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
      val x = DenseVector.fill(D)(rand.nextGaussian + y * R)
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
```
## MIT License

Copyright (c) 2011-2013

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
