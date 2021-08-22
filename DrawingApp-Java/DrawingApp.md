## Drawing Application

![Drawing App](assets/figure1.jpg "GUI Drawing App")

Choice menu for `Figure Types` Circle, Square, Cross, and Line. (All initialized with the bounding rectangle of 50px width and 50px height, except Line)

Choice menu for `Figure Actions` including:
- **None**: No action. Enable drawing mode, shapes are drawn on click based on the selected types and colors. A line is drawn using 2 clicks of 
  starting and ending points with temporary lines between the starting point and the mouse cursor.
- **Delete**: Click on a shape to remove. 
- **Enlarge**: Click on a shape to increase the size by 50% (not applicable to lines). 
- **Shrink**: Click on a shape to decrease the size by 10% (not applicable to lines). 
- **Move**: Move the shape to a new position in the drawing page. The figure moves with the mouse until it is clicked again.

Note: 
1. The location of mouse clicks are showed in the program output.
2. For Delete, Enlarge, and Move, if the point of the click is contained within more than one rectangle, only the least
   recently added component is affected.
3. For Shrink, if the point of the click is contained within more than one rectangle, only the most
   recently added component is affected.

Four `Color Options` of Red, Green, Blue, Black.

`Current Action` indicates the chosen figure type (when drawing shape), or the affected shape when actions other than None is selected. 
Figure Actions menu is set to None once an action is successfully performed, rolling back to drawing mode.