Draghici Vlad Matei 322CB

Programul se apeleaza prin comanda:
java -jar Tema2.jar < inputX.txt > outputX.txt  X = [1, 5]

Bonusuri implementate:
- comenzile bonus savestore si loadstore
- Design patternul bonus Facade
- CSVFormat si CSVPrinter din biblioteca Apache Commons

Programul contine urmatoarele clase:
1. Main - contine functia main
	- in functia main se face parsarea comenzilor de la input
	- dupa identificarea comenzii curente se apeleaza functia asociata din clasa
		Facade

2. Facade - Facade design pattern pentru bonus
	- clasa Facade contine cate o metoda asociata fiecarei comenzi de la input
	- Facade se asigura de apelarea functiilor din celelalte clase implementate
		in program si din main trebuie doar sa se apeleze metodele din main
		facand folosirea aplicatiei foarte usoara pentru o alta persoana
		care vrea sa foloseasca aplicatia
	- sunt prezente si metodele pentru bonus savestore si loadstore
	- pentru aceasta clasa Store care urmeaza sa fie salvata intr-un fisier binar
		implementeaza interfata Serializable
	- pentru savestore se foloseste ObjectOutputStream
	- pentru loadstore se foloseste ObjectInputStream

3. Store - Singleton design pattern
	- clasa implementeaza interfata Serializable pentru functiile pentru bonus
		savestore si loadstore
	Functii importante:
		- readCSV - Citirea CSV-ului se face cu clasa CSVFormat din Apache Commons
		- saveCSV - Scrierea CSV-ului se face cu clasa CSVPrinter din Apache Commons
	Alte functii:
		- addProduct
		- addManufacturer
		- changeCurrency
		- applyDiscount
	Pentru cautarea obiectelor in liste se foloseste metoda filter aplicata pe un stream
		creat din lista initiala. Pentru filter se folosesc predicate care exprima
		relatia pe care trebuie sa o respecte obiectul cautat

4. Product
	- clasa implementeaza interfata Serializable pentru functiile pentru bonus
		savestore si loadstore
	- clasa suprascrie functiile equals si toString

5. ProductBuilder - Builder design pattern
	- asigura crearea de obiecte de tip Product intr-un mod facil

Alte clase:
	- Currency
	- Discount
	- Manufacturer
	Toate aceste clase implementeaza interfata Serializable pentru a fi salvate
intr-un fisier binar si citite dintr-un fisier binar de functiile savestore si loadstore.