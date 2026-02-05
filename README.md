# 2048 Game - APCS Project

## Learning Objectives

This project reinforces key AP Computer Science A concepts from Semester 2:
- **2D Arrays**: Managing and manipulating the game board
- **ArrayList**: Storing and working with collections of data (empty cells)
- **Algorithms**: Implementing sliding and merging logic
- **Boolean Logic**: Tracking game state and valid moves
- **Nested Loops**: Traversing 2D arrays in multiple directions
- **Methods**: Breaking down complex problems into manageable functions
- **Debugging**: Using helper methods to test your logic

## Project Structure

```
src/
├── App.java        - Launcher (PROVIDED - no changes needed)
├── GUI.java        - Graphical interface (PROVIDED - no changes needed)
└── Game.java       - Game logic (YOUR IMPLEMENTATION)
```

## Understanding the Data Structures

Before you start coding, it's important to understand some key design decisions made in this project.

### Why use `int[]` to represent a cell position?

A cell on the board has **two pieces of information**: a row and a column. In Java, we need a way to store both values together.

**What we're doing**:
```java
int[] cell = new int[]{2, 3};   // row 2, column 3
int row = cell[0];              // access row
int col = cell[1];              // access column
```

**Why not use two separate variables?**
When we need to store *multiple* cell positions, we'd need parallel arrays or paired variables, which gets messy:
```java
// This would be confusing:
int[] rows = {0, 1, 2};
int[] cols = {1, 3, 0};
// Which row goes with which column?
```

**Alternative approaches** (more advanced):
- Create a `Cell` class with `row` and `col` fields (good OOP, but beyond APCS scope for this project)
- Use a `Point` class from `java.awt` (not semantically clear for a game board)

**Bottom line**: `int[]` is a simple, APCS-appropriate way to bundle coordinates together.

### Why use `ArrayList<int[]>` for empty cells?

The `getEmptyCells()` method returns `ArrayList<int[]>` - let's break this down:

**What it means**:
- `ArrayList` - a resizable list (we don't know how many empty cells there are)
- `<int[]>` - each element in the list is an `int[]` coordinate pair

**Visual example**:
```java
ArrayList<int[]> emptyCells = new ArrayList<>();
emptyCells.add(new int[]{0, 0});  // position (0,0) is empty
emptyCells.add(new int[]{0, 2});  // position (0,2) is empty
emptyCells.add(new int[]{3, 1});  // position (3,1) is empty

// Now we have a list of all empty positions we can iterate through:
for (int[] cell : emptyCells) {
    System.out.println("Empty cell at row " + cell[0] + ", col " + cell[1]);
}
```

**Why this is useful**:
1. **Dynamic sizing**: The number of empty cells changes throughout the game
2. **Random selection**: We can easily pick a random empty cell using `list.get(random.nextInt(list.size()))`
3. **Simple iteration**: We can loop through all empty cells to check game state

**Reading the syntax**:
- `ArrayList<int[]> emptyCells` → "emptyCells is a list where each item is an int array"
- `emptyCells.get(0)` → Returns an `int[]` (the first coordinate pair)
- `emptyCells.get(0)[0]` → Returns an `int` (the row of the first empty cell)

### Quick Reference: Working with Coordinates

```java
// Creating a coordinate
int[] position = new int[]{row, col};

// Adding to ArrayList
ArrayList<int[]> list = new ArrayList<>();
list.add(new int[]{2, 3});

// Retrieving and using a coordinate
int[] cell = list.get(0);        // Get first coordinate pair
board[cell[0]][cell[1]] = 2;     // Use it to access the board
```

**Common mistake to avoid**:
```java
// WRONG - trying to use the array directly as indices
int[] cell = emptyCells.get(0);
board[cell] = 2;  // ERROR! Can't use int[] as a single index

// CORRECT - extract row and col first
int[] cell = emptyCells.get(0);
int row = cell[0];
int col = cell[1];
board[row][col] = 2;  // This works!
```

## Your Task

Complete the **8 TODO sections** in [Game.java](src/Game.java). 

### TODO #1: `addRandomTile()`
**Concepts**: ArrayList, Random, conditional logic
**Task**: Add a random tile (90% chance of 2, 10% chance of 4) to an empty cell

**Step-by-step approach**:
1. Get all empty cells using `getEmptyCells()` (returns `ArrayList<int[]>`)
2. Check if the list is empty (no room for new tile) - if so, return early
3. Pick a random index from the ArrayList
4. Get the coordinate pair at that index: `int[] cell = emptyCells.get(randomIndex)`
5. Extract row and column: `cell[0]` is row, `cell[1]` is column
6. Decide if the tile should be 2 or 4 (use `random.nextInt(10) < 9` for 90% chance of 2)
7. Set that board position to the new value

**Example logic flow**:
```java
ArrayList<int[]> emptyCells = getEmptyCells();
if (emptyCells.isEmpty()) return;

int[] cell = emptyCells.get(random.nextInt(emptyCells.size()));
// Now cell[0] is the row and cell[1] is the column
board[cell[0]][cell[1]] = /* 2 or 4 based on probability */;
```

### TODO #2: `getEmptyCells()`
**Concepts**: 2D arrays, ArrayList, nested loops
**Task**: Return an ArrayList containing the coordinates of all empty cells

**Approach**: Use a nested loop to visit every cell on the board. When you find one that equals 0, bundle its row and column into an `int[]` and add it to your list. Refer to the "Working with Coordinates" section above if you're unsure about the syntax.

### TODO #3: `moveLeft()`
**Concepts**: 2D arrays, algorithms, row processing
**Task**: Slide all tiles left and merge adjacent equal tiles

**Why use a temporary array?**
We can't modify the board in place while reading it (it gets confusing!). Instead, we build the new row state in a temporary array, then copy it back.

**The Algorithm (process each row independently)**:
1. **Compress left**: Collect all non-zero values and pack them left in a temp array
   - Example: `[2, 0, 2, 4]` becomes temp: `[2, 2, 4, 0]`
2. **Merge adjacent equals**: Scan temp array for adjacent equal values
   - When found, double the first value, remove the second, update score
   - Example: `[2, 2, 4, 0]` becomes `[4, 4, 0, 0]`
   - **Important**: Only merge once per tile! `[2, 2, 2, 2]` → `[4, 4, 0, 0]` NOT `[8, 0, 0, 0]`
3. **Check for changes**: Compare temp array to original board row
4. **Copy back**: Update board[row] with temp array values

**Key Rules**:
- Each tile can only merge ONCE per move
- Merging increases the score by the merged value (not the original values)
- Only add a random tile if something actually moved
- Return `true` if any row changed, `false` otherwise

**Pseudocode structure**:
```java
boolean moved = false;
for each row:
    create temp array
    compress tiles left into temp (skip zeros)
    merge adjacent equal tiles in temp
    if (temp != original row):
        moved = true
        copy temp back to board[row]

if moved:
    addRandomTile()
return moved
```

**Example walkthrough**:
```
Original row: [2, 0, 2, 4]
After compress: [2, 2, 4, 0]
After merge: [4, 4, 0, 0]  ← tiles merged, score += 4
Final result: [4, 4, 0, 0]
```

### TODO #4: `moveRight()`
**Concepts**: Algorithm adaptation, reverse iteration
**Task**: Same as `moveLeft()` but process from right to left
**Hint**: Start from the rightmost column and work backward

### TODO #5: `moveUp()`
**Concepts**: Column processing, 2D array traversal
**Task**: Same logic as `moveLeft()` but work with columns instead of rows
**Hint**: Outer loop should iterate over columns, inner loop over rows

### TODO #6: `moveDown()`
**Concepts**: Combining vertical processing with reverse iteration
**Task**: Same as `moveUp()` but process from bottom to top
**Hint**: Start from the bottom row and work upward

### TODO #7: `hasWon()`
**Concepts**: 2D array search, boolean state management
**Task**: Check if any tile has reached the winning value (2048)
**Hints**:
- Use the `WIN_VALUE` constant (2048)
- Once won, the `hasWon` field should remain `true`
- Check all cells with nested loops

### TODO #8: `isGameOver()`
**Concepts**: Complex boolean logic, adjacency checking
**Task**: Determine if no more moves are possible
**Rules**: Game is over when:
1. No empty cells remain **AND**
2. No adjacent tiles (horizontal OR vertical) can merge

**Hints**:
- First check: `getEmptyCells().isEmpty()`
- Then check all horizontal adjacencies (same row, adjacent columns)
- Then check all vertical adjacencies (adjacent rows, same column)
- If any adjacent pair matches, moves are still possible

## Development Approach

### Recommended Order
1. Start with `getEmptyCells()` - it's used by other methods
2. Then `addRandomTile()` - test with `printBoard()`
3. Implement `moveLeft()` - this is the most complex
4. Adapt `moveLeft()` logic for the other three directions
5. Add win/loss conditions: `hasWon()` and `isGameOver()`

### Testing Strategy
- Use `printBoard()` method to debug in the console
- Test one method at a time
- Start with simple cases:
  - Can you add tiles to an empty board?
  - Does a simple slide work (no merges)?
  - Does merging work correctly?
  - What happens when the board is full?

## Running the Game

Open the project in VS Code and press F5 or click "Run" above the `main` method in [App.java](src/App.java).

### Controls
- **Arrow Keys** or **WASD**: Move tiles
- **New Game Button**: Restart the game

## Tips for Success

### Debugging Strategies
- Add `printBoard()` calls to see the board state
- Test edge cases:
  - What if a row is `[2, 2, 2, 2]`? Should become `[4, 4, 0, 0]`
  - What if a row is `[2, 0, 2, 0]`? Should become `[4, 0, 0, 0]`
  - What about `[2, 2, 4, 4]`? Should become `[4, 8, 0, 0]`

### Common Pitfalls
- **Double merging**: Make sure each tile only merges once per move
  - Example: `[2, 2, 4]` should become `[4, 4, 0]`, NOT `[8, 0, 0]`
- **Off-by-one errors**: Check your loop bounds carefully
- **Forgetting to update score**: Remember to add the merged value to score
- **Not detecting no-moves**: Check BOTH horizontal AND vertical adjacencies

## APCS Concepts Reference

### 2D Array Traversal Patterns

**By Row (Left/Right)**:
```java
for (int row = 0; row < BOARD_SIZE; row++) {
    for (int col = 0; col < BOARD_SIZE; col++) {
        // process board[row][col]
    }
}
```

**By Column (Up/Down)**:
```java
for (int col = 0; col < BOARD_SIZE; col++) {
    for (int row = 0; row < BOARD_SIZE; row++) {
        // process board[row][col]
    }
}
```

**Reverse Iteration** (Right/Down):
```java
for (int i = BOARD_SIZE - 1; i >= 0; i--) {
    // process from end to beginning
}
```

### ArrayList Operations
```java
ArrayList<int[]> list = new ArrayList<>();
list.add(new int[]{row, col});           // Add element
int[] element = list.get(index);         // Access element
boolean empty = list.isEmpty();          // Check if empty
int size = list.size();                  // Get size
```

### Random Number Generation
```java
Random rand = new Random();
int num = rand.nextInt(10);              // 0-9
boolean ninetyPercent = rand.nextInt(10) < 9;
```

## Grading Criteria

Your implementation will be evaluated on:
- **Correctness**: Does the game work as expected?
- **Code Quality**: Is your code well-organized and readable?
- **Algorithm Efficiency**: Are your solutions reasonably efficient?
- **Testing**: Does your code handle edge cases?

## Extensions (Optional Challenges)

Once you've completed the base project, try these enhancements:
- Add an undo feature (store previous board states)
- Implement a score-based difficulty system
- Add animations for tile movements
- Create a "best score" tracker using file I/O
- Implement different board sizes (3x3, 5x5)

## Getting Help

- Review the method signatures and return types carefully
- Use `printBoard()` liberally to debug
- Test one method at a time
- Draw out the board states on paper
- Think about the problem step-by-step before coding

Good luck, and have fun implementing 2048!