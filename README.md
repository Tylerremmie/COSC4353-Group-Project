
# COSC4353-Group-Project [![Build Status](https://travis-ci.org/Tylerremmie/COSC4353-Group-Project.svg?branch=master)](https://travis-ci.org/Tylerremmie/COSC4353-Group-Project) [![codecov](https://codecov.io/gh/Tylerremmie/COSC4353-Group-Project/branch/master/graph/badge.svg)](https://codecov.io/gh/Tylerremmie/COSC4353-Group-Project)


##### Dan Martinez, Marc Cardenas, Daria Martin, Tyler Remmie

##### RISK boardgame:

javac -d bin src/main/java/cosc4353/*.java

java -cp bin cosc4353.Main

# Updates:

**v.0.4**
- [X] Notify players if their territories are under attack.
- [X] Players can purchase in-game credit. Players can use the credit to buy cards, buy undo actions, or transfer the credits to another player.
- [X] Post the number of territories conquered by each player on Twitter after each turn and at the end of the game [Reference1](https://developer.twitter.com/en/docs/developer-utilities/twitter-libraries.html) or [Reference2](http://twitter4j.org/en/index.html)
- [X] Modify pom.xml to generate JavaDocs and class diagrams [Reference](https://maven.apache.org/plugins/maven-javadoc-plugin/examples/alternate-doclet.html)

**v.0.3**
- [X] Users can undo their actions
- [ ] Your Program uses Amazon S3 to replay games. (You get some Amazon credits through your GitHub student pack)
- [X] Test cases to cover at least 50% of the code. 
- [X] Show test coverage on the repository page.

**v.0.2**
- [X] Use Travis CI and show the status of the build.