# Remind (CLI App)

## Commands 
- ### memo
`memo` + `message` store new message 
  <pre>
  Command? <i><b>memo Today is my graduation day!</b></i>
  </pre>
`memo` list all memos
  <pre>
  Command? <i><b>memo </b></i>
  </pre>
  
- ### phone
`phone` + `name` + `phone number` store new contact
  <pre>
  Command? <i><b>phone Alex 0123456789</b></i>
  </pre>
`phone` + `name or character(s)` search contact 
  <pre>
  Command? <i><b>phone Alex </b></i>
  Command? <i><b>phone A </b></i>
  </pre>
`phone` list all contacts  
  <pre>
  Command? <i><b>phone </b></i>
  </pre>
`phone` + `-a` list all contact alphabetically
  <pre>
  Command? <i><b>phone -a</b></i>
  </pre>
- ### todo
`todo` +`date` + `message` store new todo
  <pre>
  Command? <i><b>todo 31/3/2021 Call Alex</b></i>
  </pre>
`todo` + `date` list of todos fall in the searched date as well as the previous and the following dates of the searched date
  <pre>
  Command? <i><b>todo 31/3/2021 </b></i>
  </pre>
`todo` list all todos 
  <pre>
  Command? <i><b>todo </b></i>
  </pre>
`todo` + `-d` list all todos sort by date
  <pre>
  Command? <i><b>todo -d</b></i>
  </pre>
- `list` list all memos, contacts and todos
  <pre>
  Command? <i><b>list</b></i>
  </pre>
- `exit` exit the program
  <pre>
  Command? <i><b>exit</b></i>
  </pre>

**Note**:
- the `list` command displays the items in the same order that they were entered
- the program's prompt (`Command?`) appears on the same line as the command
  you type
- there's a single blank line between each command.





