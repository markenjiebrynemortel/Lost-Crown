<h1 align = "center">⚔️🛡️The Lost Crown 👑</h1>
<h3 align = "center"></h3>
<p align = "center">
<b>BSIT-2114 </b> <br/>
Mendoza, Thimoty Bhourex <br/>
Mortel, Markenjie Bryne B. <br/>
Vergara, Charles Alwyn L.

</p>
<h2> 🔍 Overview</h2>
<p>
The entire game is structured across several logical sections to maximize code reusability and demonstrate the OOP principles which are Encapsiulation, Inheritance, Polymorphism, Abstraction and Exception Handling</br>  
Lost Crown utilizes the concepts of Object-Oriented Program such as encapsulation, inheritance, polymorphism, and abstraction
</p></br>

<h3> ⛾ Object-oriented Principles</h3>
<p>
  
<h2>✳️Encapsulation</h2>
Definition: Bundling data fields and the methods that operate on that data into a single unit as a class, and restricting direct access to the data.
​<h3>Demonstration in Code:</h3>
All data fields in AbstractCharacter, Weapon, and Armor are declared as private. Access and modification are strictly controlled using public getter and setter methods, such as getHealth() and setHealth().</br> </br>

<h2>✳️Inheritance</h2>
Definition: A mechanism where one class inherits the fields and methods of another class, promoting code reuse and establishing a clear hierarchy.
<h3>​Demonstration in Code:</h3>
​PlayerCharacter extends AbstractCharacter.
​Knight, Mage, etc., extend PlayerCharacter.
​This establishes a clear class hierarchy using superclasses and at least three subclasses as required.  </br></br>

<h2>✳️​Polymorphism</h2>
Definition: The ability of an object to take on many forms, typically allowing methods to behave differently based on the object type calling them.
​<h3>Demonstration in Code:</h3>
​Method Overriding: Each specific character class overrides the specialAttack() method to define its unique logic.
​Dynamic Behavior: The startBattle method handles both players and monsters through AbstractCharacter references, and the correct, specific specialAttack() implementation is called at runtime.</br></br>

<h2>✳️​Abstraction</h2>
​Definition: Hiding the complex implementation details and showing only the necessary features to the user.
​<h3>Demonstration in Code:</h3>
​The abstract class AbstractCharacter and the abstract void specialAttack() method force the core design but hide the individual implementation details. Like when the AbstractCharacter doesn't need to know how a Mage attacks, only that it must have an attack.</br></br>

​<h2>✳️Exception Handling</h2>
​Definition: Providing a mechanism to handle runtime errors gracefully, such as invalid user input.
​<h3>Demonstration in Code:</h3>
The try-catch block is used in the selectCharacter() and startBattle() methods to catch InputMismatchException when the user enters text instead of a number, preventing the program from crashing.
</p></br>

<h2> 🖧 Breakdown of the Code</h2>
<p>
<h3>Segmented Java Code Breakdown:<h3>
<h3>⚪ ​1. Item Classes: Weapon.java and Armor.java</h3>
​These classes are responsible for defining and holding the properties of equippable items.
Both classes keep their fields like the attackModifier and name private. Access to these fields is only permitted through public getter methods like the getAttackModifier()). This protects the data from unauthorized or inconsistent external modification.</br></br>

<h3>⚪ 2. Base Classes: AbstractCharacter and PlayerCharacter</h3>
​This is the core of the project's Inheritance and Abstraction structure.</br>

​A. AbstractCharacter.java</br>
​This abstract class establishes the minimum requirements and shared mechanics for all combat participants, the players and monsters.

B. PlayerCharacter.java</br>
​This abstract class is a layer between the generic AbstractCharacter and the specific player classes.</br></br>

<h3>⚪ 3. Concrete Subclasses: Knight, Mage, Archer, Assassin, Dwarf, and Monster.</h3>
​These classes define the unique combat identities, primarily demonstrating Polymorphism.

The game engine can treat a Knight and a Mage uniformly as an AbstractCharacter. When the method player.specialAttack(target) is called, Java automatically determines and executes the correct, specific code block for the actual type of character (Knight.specialAttack or Mage.specialAttack), which is dynamic behavior through superclass references.</br></br>

<h3>⚪ 4. Game Engine: FinalProjectGame.java</h3>
​This class contains the main method and controls the entire program flow, demonstrating Exception Handling and the use of Arrays.
</p></br>

<h3>👤 Members </h3>
<table>
<tr>
    <th> &nbsp; </th>
    <th> Name </th>
</tr>
<tr>
    <td><img src="PICS/thimoty.jpg" width="100" height="100"> </td>
    <td><strong>Mendoza, Thimoty Bhourex</strong> <br/>
        </a>
    </td>
  
</tr>
<tr>
    <td><img src="PICS/kenjie.jpg" width="100" height="100"> </td>
    <td><strong>Mortel, Markenjie Bryne B.</strong> <br/>
        </a>
    </td>
   
</tr>
<tr>
    <td><img src="PICS/charles.jpg" width="100" height="100"> </td>
    <td><strong>Vergara, Charles Alwyn L.</strong> <br/>
        </a>
    </td>
  
</tr>
</table></br>

<h3>📚 Acknowledgement</h3>
<p>
We sincerely express our gratitude to our instructor for the guidance and support provided throughout the completion of this project. We also extend our appreciation to our classmates and peers for their cooperation and encouragement during the development process.
</p></br>

<h3> ⚠️ DISCLAIMER</h3>
<p>
This project and its contents are provided for example and learning purposes only. Students are encouraged to use it as a reference and not copy it in its entirety.
</p></br>


