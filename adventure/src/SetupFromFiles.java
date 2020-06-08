import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Alex Cohen
 */
public class SetupFromFiles {
	public static ArrayList<Room> initializeRooms ( String filename )
	    throws FileNotFoundException {
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		ArrayList<Room> rooms = new ArrayList<Room>();

		// initialize all rooms without any connections
		for ( int i = 0 ; scanner.hasNextLine() ; i++ ) {
			rooms.add(new Room());
			rooms.get(i).setName(scanner.nextLine());
			scanner.nextLine();
			String line = "";
			int descriptionCt = Integer.parseInt(scanner.nextLine());
			String description = scanner.nextLine();
			for ( int ct = 0 ; ct < descriptionCt ; ct++ ) {
				for ( ; scanner.hasNextLine() ; ) {
					line = scanner.nextLine();
					if ( line.equals("END") ) {
						break;
					}
					description = description + " " + line;
				}

				if ( ct == 0 ) {
					rooms.get(i).setLongDescription(description);

				} else if ( ct == 1 ) {
					rooms.get(i).setInitialDescription(description);
				}
				description = "";
			}
		}

		// initialize room connections
		Map<Integer,String> directionMap = new HashMap<Integer,String>();
		directionMap.put(0,"north");
		directionMap.put(1,"south");
		directionMap.put(2,"east");
		directionMap.put(3,"west");
		scanner = new Scanner(file);
		for ( int i = 0 ; i < rooms.size() ; i++ ) {
			scanner.hasNextLine();
			scanner.nextLine();
			String[] directions = (scanner.nextLine().split(","));
			for ( int j = 0 ; j < directions.length ; j++ ) {
				directions[j] = directions[j].trim();
				if ( directions[j].equals("-") == false ) {
					int k = 0;
					for ( ; k < rooms.size() - 1 ; k++ ) {
						if ( (rooms.get(k).getName()).equals(directions[j]) ) {
							break;
						}
					}
					rooms.get(i).addDirection(directionMap.get(j),rooms.get(k));
				}
			}
			String line = "";
			int descriptionCt = Integer.parseInt(scanner.nextLine());
			String description = scanner.nextLine();
			for ( int ct = 0 ; ct < descriptionCt ; ct++ ) {
				for ( ; scanner.hasNextLine() ; ) {
					line = scanner.nextLine();
					if ( line.equals("END") ) {
						break;
					}
					description = description + " " + line;
				}

				if ( ct == 0 ) {
					rooms.get(i).setLongDescription(description);

				} else if ( ct == 1 ) {
					rooms.get(i).setInitialDescription(description);
				}
				description = "";
			}
		}
		scanner.close();

		return rooms;
	}

	public static void initializeItems ( String filename, List<Room> rooms )
	    throws FileNotFoundException {
		File file = new File(filename);
		Scanner scanner = new Scanner(file);

		// initialize all items
		for ( int i = 0 ; scanner.hasNextLine() ; i++ ) {
			Item item = new Item(scanner.nextLine());

			int j = 0;
			String place = scanner.nextLine();
			for ( ; j < rooms.size() ; j++ ) {
				if ( rooms.get(j).getName().equals(place) ) {
					rooms.get(j).addItem(item);
					break;
				}
			}

			String line = "";
			String description = scanner.nextLine();
			for ( ; scanner.hasNextLine() ; ) {
				line = scanner.nextLine();
				if ( line.equals("END") ) {
					break;
				}
				description = description + " " + line;
			}
			item.setDescription(description);
		}

	}

	public static Objectives initializeScoring ( Player player, String filename,
	                                             List<Room> rooms,
	                                             Objectives objectives )
	    throws FileNotFoundException {
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		String[] details = null;
		// initialize all items
		for ( ; scanner.hasNextLine() ; ) {
			String action = scanner.next();
			details = scanner.nextLine().split(",");
			if ( action.equals("drop") ) {
				if ( scanner.hasNext("description") ) {
					scanner.nextLine();
					String description = buildDescription(scanner);
					objectives.getDropObjectives()
					    .add(new DropObjective(new Item(details[0]),new Room(details[1]),
					                           Integer.parseInt(details[2]),player,
					                           description));
				} else {
					objectives.getDropObjectives()
					    .add(new DropObjective(new Item(details[0]),new Room(details[1]),
					                           Integer.parseInt(details[2]),player));
				}
			} else if ( action.equals("visit") ) {
				objectives.getVisitObjectives()
				    .add(new VisitObjective(new Room(details[0]),
				                            Integer.parseInt(details[1]),player));
			} else if ( action.equals("goal") ) {
				if ( scanner.hasNext("description") ) {
					scanner.nextLine();
					String description = buildDescription(scanner);
					objectives.getGoalObjectives()
					    .add(new GoalObjective(new Room(details[0]),
					                           Integer.parseInt(details[1]),player,
					                           description));
				} else {
					objectives.getGoalObjectives()
					    .add(new GoalObjective(new Room(details[0]),
					                           Integer.parseInt(details[1]),player));
				}
			} else {
				throw new IllegalArgumentException("Please use a valid scoring file");
			}
		}
		// details[details.length-1];

		return objectives;
	}

	private static String buildDescription ( Scanner scanner ) {
		String line = "";
		String description = scanner.nextLine();
		for ( ; scanner.hasNextLine() ; ) {
			line = scanner.nextLine();
			if ( line.equals("END") ) {
				break;
			}
			description = description + " " + line;
		}
		return description;
	}

}
