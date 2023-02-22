import java.awt._
  import java.awt.image.BufferedImage
  import java.io._
  import javax.swing._
  import javax.swing.event.{ ChangeEvent, ChangeListener }
  import javax.imageio.ImageIO

object ViewImage {

  type EffectDisplayImage = Unit

  type ViewImage = (BufferedImage, String) => EffectDisplayImage
  lazy val viewImage: ViewImage = (bi, title) => {

    val frame = new JFrame(title)

    val image = new ImageComponent(bi)

    // add the image content to the JFrame
    // and
    // make the JFrame scrollable & zoomable
    frame.add(
      {
        val slider = new JSlider(0, 1000, 500)
        slider.addChangeListener(new ChangeListener() {
          override def stateChanged(e: ChangeEvent): Unit =
            image.setZoom_mut((2.0 * slider.getValue) / slider.getMaximum)
        })
        slider
      },
      BorderLayout.NORTH
    )
    frame.add(new JScrollPane(image))

    // size it, make the close button hide the window, and make it visible
    frame.setSize(800, 600)
    frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE)
    frame.setVisible(true)
  }

  class ImageComponent(val img: BufferedImage) extends JComponent {

    setZoom_mut(1.0)

    override def paintComponent(g: Graphics): Unit = {
      super.paintComponent(g)
      val dim = getPreferredSize
      val _ = g.drawImage(img, 0, 0, dim.width, dim.height, this)
    }

    def setZoom_mut(zoom: Double) = {
      val w = (zoom * img.getWidth()).toInt
      val h = (zoom * img.getHeight()).toInt
      setPreferredSize(new Dimension(w, h))
      revalidate()
      repaint()
    }
  }

}

def testDraw(): Unit =
	var testImage = BufferedImage(500, 400, 1)
	var testImportImage = ImageIO.read(new File("D:\\2. Uni\\Winter 2022 2023\\Object meets Funkiton\\Workspace\\Project\\OmF-Project-M12\\aeons-end\\src\\main\\scala\\BoardBackground.jpg"))
	ViewImage.viewImage(testImportImage, "Test Draw")
	