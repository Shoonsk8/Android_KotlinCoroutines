# Kotlin Coroutine

Take your recycler view you built previously, adapt it to use coroutines instead of a Thread object. Add a detail view.

## Requirements

Take your existing recycler view, change it to perform the network calls with coroutines. Add a detail view. In this view, perform another network call with a coroutine

## Steps

1. Change ListAdapter, DAO, and NetworkAdapter to use coroutines.
2. Add a detail view for the items in the list
3. In that view, perform another network call
   1. This can be downloading a image, or performing any other call if your api doesn't support images

## Go Further

- Add stretch goals from previous assignment
  - Add a header and footer to your view
  - Add pagination (load more items when you get to the end of the existing ones)
- Polish the detail view.
