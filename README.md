<h1 align = "center">⚔️🛡️The Lost Crown 👑</h1>
<h3 align = "center"></h3>
<p align = "center">
<b>BSIT-2114 </b> <br/>
Mendoza, Thimoty Bhourex <br/>
Mortel, Markenjie Bryne B. <br/>
Vergara, Charles Alwyn L.

</p>
<h3> 🔍 Overview</h3>
<p>
The entire game is structured across several logical sections to maximize code reusability and demonstrate the OOP principles which are Encapsiulation, Inheritance, Polymorphism, Abstraction and Exception Handling</br>  
Lost Crown utilizes the concepts of Object-Oriented Program such as encapsulation, inheritance, polymorphism, and abstraction
</p></br>

<h3> ⛾ Object-oriented Principles</h3>
<p></p>
  
<h2>✳️Encapsulation</h2>
All attributes within the Combatant abstract base class (e.g., health, attack, defense) are declared as protected and accessed via public getter methods (getHealth(), getName(), etc.), ensuring controlled and safe access to the object's state.</br>

<h2>✳️Inheritance</h2>
​A clear class hierarchy is established:
​Combatant (Base Abstract Class) is the parent of:
​PlayerCharacter (Abstract Class)
​Enemy (Base Class)
​Concrete player classes (Knight, Archer, Assassin) inherit from PlayerCharacter.
​Specific enemy types (Goblin, Chaus, etc.) inherit from Enemy.

<h2>✳️​Polymorphism</h2>
​The battle logic operates on generic Combatant references (player and enemy). When methods like basicAttack() or specialAttack() are called, the specific implementation defined by the concrete class (e.g., Archer vs. Assassin) is executed at runtime.

<h2>✳️​Abstraction</h2>
​The abstract class Combatant defines the necessary structure and methods (like basicAttack and specialAttack) that all fighting entities must implement, concealing the complex, class-specific damage and ability logic.

​<h2>✳️Exception Handling</h2>
​In the BattleSimulator class, the getValidInput method uses a try-catch block to safely handle non-integer input from the user during move selection, providing clear error messages and preventing the program from crashing.
</p></br>

<h3>Breakdown of the Code</h3></br>
<p>
Segmented Java Code Breakdown
  
​1. Item Classes: Weapon.java and Armor.java
​These classes are responsible for defining and holding the properties of equippable items.
Explanation of Encapsulation:
Both classes keep their fields. Access to these fields is only permitted through public getter methods like getAttackModifier()). [cite_start] This protects the data from unauthorized or inconsistent external modification.

2. Base Classes: AbstractCharacter and PlayerCharacter
​This is the core of the project's Inheritance and Abstraction structure.

​A. AbstractCharacter.java
​This abstract class establishes the minimum requirements and shared mechanics for all combat participants (players and monsters).
B. PlayerCharacter.java
​This abstract class is a layer between the generic AbstractCharacter and the specific player classes.
4. Concrete Subclasses: Knight, Mage, Archer, Assassin, Dwarf, and Monster
​These classes define the unique combat identities, primarily demonstrating Polymorphism.
Explanation of Polymorphism (Method Overriding):
[cite_start]Polymorphism means "many forms". The game engine can treat a Knight and a Mage uniformly as an AbstractCharacter. [cite_start]When the method player.specialAttack(target) is called, Java automatically determines and executes the correct, specific code block for the actual type of character (Knight.specialAttack or Mage.specialAttack), which is dynamic behavior through superclass references. 
5. Game Engine: FinalProjectGame.java
​This class contains the main method and controls the entire program flow, demonstrating Exception Handling and the use of Arrays.
</p>

<h3>🎯 Example Output</h3>
<p>
(Screenshot of the Game)</br>
</p></br>

<h3>👤 Members </h3>
<table>
<tr>
    <th> &nbsp; </th>
    <th> Name </th>
</tr>
<tr>
    <td><img src="static/marieemoiselle.JPG" width="100" height="100"> </td>
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


