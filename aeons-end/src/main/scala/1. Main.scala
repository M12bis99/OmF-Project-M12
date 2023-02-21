@main def gameController: Unit = 
	//initialize Nemesis, Mage and Shop
	// Note: Menu to select Nemesis to fight does not exist yet, since only one is implemented
	val nemesis: Nemesis = initializeBladius()
	//Note: Menu to select Mage also missing, since only one is implemented
	val mage: Mage = initializeBrama()

	//Note: Menu to select/randomize Shop layout also missing, since only a few cards are implemented
	// placeholder Initialization
	val shop: Shop = initializeShop()

	//create GameState
	val gameState: GameState = GameState(nemesis, mage, shop)

	//start the Game
	//gameState.gameStart()
	
	//testDraw()