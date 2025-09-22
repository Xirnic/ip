# Kjaro Task Manager

Kjaro is a chatbot that will manage your tasks! With Kjaro's help, you can better manage those pesky <ins>todos</ins>, those daunting <ins>deadlines</ins>, and those upcoming <ins>events</ins>!

## Features

- The general form for Kjaro's commands are: `commandword` `arguments`
- Date inputs are only in the `yyyy-mm-dd` format.

### List
Lists out all of your tasks.  
Format: `list`

### Save
Saves all of your data.  
Format: `save`

### ToDo
Adds a todo to your task list.  
Format: `todo` `taskname`  
Examples:  
- `todo clean my room`
- `todo vacuum the floor`

### Deadline
Adds a deadline to your task list, includes a due date.  
Format: `deadline` `taskname` `/by` `due date`  
Examples:
- `deadline math assignment /by 2025-08-23`
- `deadline job application /by 2025-09-22`

### Event
Adds an event to your task list, includes a start and end date.  
Format: `event` `taskname` `/from` `startdate` `/to` `enddate`
Examples:
- `event summer vacation /from 2024-06-10 /to 2024-06-28`
- `event convention preparation /from 2025-01-22 /to 2025-01-29`

### Snooze
Delays a task for the chosen number of days, default is one day.  
Format: `snooze` `tasknumber` `/for` `days`  
Examples:
- `snooze 1`
- `snooze 5 /for 3`

### Marking tasks
Mark a task as complete or incomplete
Format: `mark / unmark` `tasknumber`  
Examples:
- `mark 5`
- `unmark 3`

### Deleting tasks
Deletes a task by task number.  
Format: `delete` `tasknumber`  
Examples:
- `delete 1`
- `delete 4`

### Finding tasks by name
Finds a task by a keyword / keyphrase.  
Format: `find` `keyword`  
Examples:
- `find vacation`
- `find project work`
