import scala.collection.mutable.ArrayBuffer
class Shop:
	var cardArray: ArrayBuffer[MageCard] = new ArrayBuffer[MageCard]()
	var amountArray: ArrayBuffer[Int] = ArrayBuffer[Int](7, 7, 5, 5, 5, 5)
end Shop

def initializeShop(): Shop = 
	val cards: List[MageCard] = initializeShopCards()
	val shop: Shop = Shop()

	def fillShop(): Unit = cards match
		case Nil => ()
		case card::xs => shop.cardArray += card
	
	fillShop()
	shop