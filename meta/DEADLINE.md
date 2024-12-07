# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

My app is a book recommendation app based on what anime you like. When
you enter an anime name and click the find books button, it will display
5 book recommendations based on the genre of the anime. I used Jikan API
for the anime. This API is essentially a database for a website that has
information about almost every anime. I used Open Library for the books.
They are connected as follows: Jikan API is queried using the anime name
then the genres are extracted from the JSON response and these genres are
used to query the Open Library API, which gives you information about books
in the same genre.

GitHub URL: https://github.com/yousef-0614/cs1302-api-app.git

## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### Jikan API

```
https://api.jikan.moe/v4/anime?q=bleach
```

> Replace this line with notes (if needed) or remove it (if not needed).

### Open Library API

```
https://openlibrary.org/search.json?q=mystery&limit=5
```

> Replace this line with notes (if needed) or remove it (if not needed).

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

I learned how to actually make things while coding. Before the this project, I understood how to code sure,
but I wasn't really sure how to actually build things. This project helped a lot with GitHub and using
concepts like APIs to make functioning apps.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

I'm not really sure I think all the mistakes I made are important. I would choose a different idea
though just so I can try more new things.
