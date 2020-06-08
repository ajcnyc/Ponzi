import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author Alex Cohen
 */
public class Ponzi {
	private static Player player_;
	private static Objectives objectives_ = new Objectives();
	private static boolean end_ = false;

	/**
	 * @param args
	 */
	public static void playGame () {
		try {
			// initialize all rooms from the file with their items
			// initialize the room connections
			ArrayList<Room> rooms = SetupFromFiles.initializeRooms("Rooms");

			// initialize item placement in rooms
			SetupFromFiles.initializeItems("Items",rooms);

			// initialize the player_ and their bag
			player_ = new Player("Me",0,new Bag(10),rooms.get(0));

			// initialize collections for the drop, visit, and goal objectives //from
			// that file
			SetupFromFiles.initializeScoring(player_,"Scoring",rooms,objectives_);

		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
		}

		// allow the player_ to move between rooms and manipulate objects
		// keep track of the objectives the player_ has achieved
		// If the player_ has achieved all objectives and is in the final //room end
		// the game
	}

	public static void run ( String text, TextArea f ) {
		// prompt the player_ with the description and their options for the first
		// room
		Scanner scanner = new Scanner(System.in);
		String input = "";
		Room currentRoom = player_.getLocation();
		currentRoom.incrementVisits();
		System.out.println(currentRoom.getName());
		if ( currentRoom.getVisits() == 1 ) {
			System.out.println(currentRoom.getLongDescription());
			f.appendText(currentRoom.getLongDescription() + "\n");

			if ( currentRoom.getInitialDescription() != null ) {
				System.out.println(currentRoom.getInitialDescription());
				f.appendText(currentRoom.getInitialDescription() + "\n");
			}
		}
		HashMap neighbors = (HashMap<String,Room>) currentRoom.getDirectionMap();
		Collection<String> directions = neighbors.keySet();
		String key = null;
		for ( String direction : directions ) {
			if ( direction != null ) {
				key = direction.toString();
				String value =
				    ((Room) (currentRoom.getDirectionMap().get(direction))).getName();
				System.out.print(key + ": " + value + ", ");
				f.appendText(key + ": " + value + ", " + "\n");
			}
		}
		System.out.println();
		System.out.print("> ");
		f.appendText("> ");
		input = text;
		handleCommand(player_,input);
		if ( end_ == true ) {
			Platform.exit();
		}
	}

	public static boolean handleCommand ( Player player_, String input ) {
		input = input.toLowerCase().trim();
		String[] line = input.split(" ",2);
		if ( line.length == 1 ) {
			if ( line[0].equals("n") ) {
				line[0] = "north";
			} else if ( line[0].equals("s") ) {
				line[0] = "south";
			} else if ( line[0].equals("e") ) {
				line[0] = "east";
			} else if ( line[0].equals("w") ) {
				line[0] = "west";
			}
			if ( line[0].equals("north") || line[0].equals("south")
			    || line[0].equals("east") || line[0].equals("west") ) {
				if ( !validateDirection(line[0]) ) {
					return false;
				}
				Room nextRoom = player_.getLocation().getDirectionMap().get(line[0]);
				player_.move(line[0]);
				if ( goalCheck() ) {

				}
			} else if ( line[0].equals("look") ) {
				System.out.println(player_.getLocation().getLongDescription());

				for ( Item i : player_.getLocation().getItems() ) {
					System.out.println(i.getName());
				}
			} else if ( line[0].equals("inventory") ) {
				for ( Item i : player_.inventory() ) {
					System.out.println(i.getName());
				}
			} else if ( line[0].equals("score") ) {
				System.out.println(player_.getscore());
			} else if ( line[0].equals("quit") ) {
				Platform.exit();
				System.exit(0);
			}
		} else if ( line.length == 2 ) {
			if ( line[0].equals("go") || line[0].equals("move") ) {
				if ( line[1].equals("north") || line[1].equals("south")
				    || line[1].equals("east") || line[1].equals("west") ) {
					if ( !validateDirection(line[1]) ) {
						return false;
					}
					/*
					 * GOAL ROOM CHECK: Check to see if the player is trying to move to
					 * the goal room and only allow them to go there if they have the
					 * maximum number of points that they can get minus the points they
					 * get for visiting the goal room
					 */
					Room nextRoom = player_.getLocation().getDirectionMap().get(line[1]);
					player_.move(line[1]);
					if ( goalCheck() ) {

					}

				}
			} else if ( line[0].equals("take") || line[0].equals("get") ) {
				boolean roomContains = false;
				for ( Item i : player_.getLocation().getItems() ) {
					if ( i.getName().toLowerCase().equals(line[1]) ) {
						player_.getLocation().getItems().remove(i);
						player_.take(i);
						roomContains = true;
						System.out.println("You took " + i.getName());
						break;
					}
				}
				if ( !roomContains ) {
					return false;
				}
			} else if ( line[0].equals("drop") ) {
				Item thisItem = null;
				for ( Item i : player_.inventory() ) {
					if ( i.getName().toLowerCase().equals(line[1]) ) {
						player_.drop(i);
						for ( Item n : player_.getLocation().getItems() ) {
							System.out.println(n.getName());
						}
						for ( DropObjective o : objectives_.getDropObjectives() ) {
							o.updateComplete(player_,player_.getLocation());
						}
						thisItem = i;
						for ( DropObjective o : objectives_.getDropObjectives() ) {
							if ( o.isComplete() == true && thisItem != null
							    && thisItem.getName().trim()
							        .equals(o.getItem().getName().trim())
							    && player_.getLocation().getName().trim()
							        .equals(o.getRoom().getName().trim()) ) {
								System.out.println(o.getMessage());
							}
						}
					}
				}
			} else if ( line[0].equals("look") ) {

				boolean roomContains = false;
				for ( Item i : player_.getLocation().getItems() ) {
					if ( i.getName().toLowerCase().equals(line[1]) ) {
						System.out.println(i.getDescription());
						roomContains = true;
						break;
					}
				}
				boolean inventoryContains = false;
				for ( Item i : player_.inventory() ) {
					if ( i.getName().toLowerCase().equals(line[1]) ) {
						System.out.println("you have " + i.getName());
						System.out.println(i.getDescription());
						roomContains = true;
						break;
					}
				}
				if ( !roomContains && !inventoryContains ) {
					return false;
				}
			}
		}
		for ( VisitObjective o : objectives_.getVisitObjectives() ) {
			o.updateComplete(player_);
			// System.out.println(player_.getLocation().getName());
			// System.out.println("v: "+player_.getscore());
		}
		return true;
	}

	private static boolean validateDirection ( String command ) {
		HashMap neighbors =
		    (HashMap<String,Room>) player_.getLocation().getDirectionMap();
		Collection<String> directions = neighbors.keySet();
		String key = null;
		boolean validDirection = false;
		for ( String direction : directions ) {
			if ( direction != null ) {
				key = direction.toString();
				if ( command.equals(direction) ) {
					validDirection = true;
				}
			}
		}
		return validDirection;
	}

	/*
	 * GOAL ROOM CHECK: Check to see if the player is trying to move to the goal
	 * room and only allow them to go there if they have the maximum number of
	 * points that they can get minus the points they get for visiting the goal
	 * room
	 */
	private static boolean goalCheck () {
		if ( objectives_.getGoalObjectives().get(0)
		    .checkRoom(player_.getLocation()) ) {
			int goalObjectiveTotal = 0;
			for ( Objective o : objectives_.getGoalObjectives() ) {
				goalObjectiveTotal = goalObjectiveTotal + o.getPoints();
			}
			if ( player_.getscore() == objectives_.getMaxScore()
			    - goalObjectiveTotal ) {
				end_ = true;
				System.out.println(objectives_.getGoalObjectives().get(0).getMessage());
				return true;
			} else {
				return false;
			}
		} else {
			// vacuously true
			return true;
		}
	}
}
