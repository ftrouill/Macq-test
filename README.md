# Technical case M<sup>3</sup> - Macq - Franck TROUILLEZ

This is the technical case given by Macq for Franck TROUILLEZ.

## Technologies

The project is basically to develop a web application using the following technologies:
- Git
- Angular
- MongoDB
- Play! Framework (Scala)

In this case, *Git* is used for software development, *Angular* to run the frontend application, *MongoDB* for the database and *Play!* for the backend server. 

## User story

The user story is described below:

*The user should be able to log in your web application, using
a username and a password. The system will check the
credentials before login the user.*

*Once the user is logged, he can see a list of horses in the
application. The list is a table with different columns:*
- *Horse's name*
- *Colour*
- *Speed*
- *Breed*
- *Image*

*The user can create a new horse with all these fields*

## Assumption about the user story

While reading the user story, I interpreted that the client want an application that allows a user to login, and to manage his own database of horses. Hence, it is not a common database for all the users. It is done by allowing a MongoDB collection to each user, where he can freely add/edit/delete horses to his table.

## How to run the application

Before trying to run the application, make sure the nodes are installed for the frontend application.
`./install-nodes`

You need to first have a running MongoDB. In this technical case, a local database hosted on the default port (27017) is used.
`./run-db`

Then, you need to start the backend server. In this case, a local server running on the default port (9000) is started.
`./run-backend`


Also, you need to run the frontend application. For this case, the local server will run on the default port (4200).
`./run-frontend`


Lastly, connect to a browser at the address running the *Angular* application, which is hosted on `localhost:4200`.
`./run-browser`

**Remark:** If you didn't manage to start the scripts, you can simply run the commands manually.

For the nodes installation, make sure you are in the `frontend` directory and run this.
`npm install`

For the database, run
`mongod`

For the backend server, move to the `backend` folder and run this.
`sbt run`

For the frontend applicaiton, move to the `frontend` folder and run this.
`ng serve`

For the browser, which was *Google Chrome* when developping this application, you need to run this.
`google-chrome "http://localhost:4200" --disable-web-service --user-data-dir="/tmp/chrome_dev_test"`

**Remark:** When I tried to connect the frontend with the backend server, I had some issues CORS issues. In order to still make the application work, I needed to use *Google Chrome* without the CORS, by running:
`google-chrome --disable-web-security --user-data-dir="/tmp/chrome_dev_test"`
I know it is also possible to disable such filters in the application itself, but I had not a lot of time and it was the easiest fix.

## Limitations

Compared to the user story, there are some limitations in this implementation.

### Backend

- This implementation assumes that someone that tries to login has already an account. If the account given to the server is wrong (the combination of `username`-`password` is not recognized), an internal error occurs. I didn't manage to find how to check if a given query is empty or not. With that point, it would be easy to first check that the query is empty and to return a `BadRequest` to the client, instead of throwing an internal error.
- This implementation doesn't use JWT for the authentication. I knew the principle of JWT but I never implemented it myself. Since I had to wrap everything in a week, I didn't focus on that part.
Currently, the token for the client is the `_id` from the MongoDB corresponding to its account. It is of course not a good practice in a real application.
Using JWT, a good practice to ensure authentication would be to require the client to send a `POST` request with its `username` and `password`, as done in this project. Then, the server would generate a token based on the `user_id` (could be the username is unique, or the MongoDB object ID) using JWT, and then send it back to the client, in order to identify him for the next requests. Moreover, in order to reinforce the security, one could use the idea of access and refresh tokens. The access token would be valid for a short period, and the server would require the refresh token of the client to give a new access token when the previous one is expired.
- The user story stated that the user should be able to store a horse with a field named 'Image'. I never used MongoDB to store images and I hence considered in this implementation that 'Image' is a text field, as well as the rest of the attributes of a horse. I know that I should have use *GridFS* for example, in order to store images in the database, but I had not the time to investigate more in this week. Another option would be to store the image on an image host online, and to just store the url to this image in the database.
- The user information is stored as a clear text, which is of course not feasible in reality. One way to do this in reality is to encrypt the user information (`username` and `password`), in order to not clearly store the user information.

As a last remark on the backend part, I had never used *Scala* nor *Play! Framework* before this technical test. I am sure that I have a lot to learn in order to produce something that is clear and clean, but here is the result with less than a week.

### Frontend

For the frontend part, the base application is based on a work already done by Jason Watmore. You can find his work [here](https://jasonwatmore.com/post/2020/04/28/angular-9-user-registration-and-login-example-tutorial). I am more confortable in the backend part of the application than in the frontend part. Moreover, I also wanted to get rid of the design part of the work, which I think was less relevant than the backend and frontend part. I adapted his work to my application.

- Since the backend gives an internal error when an unknown user tries to log in, a bad alert also shows on the screen.
- Once logged in, the horses are given in a table, but the image field is just a text field, as stated before. I didn't include the feature to upload an image, knowing that on the backend part, it was only dealing with strings.

As a last remark about the frontend part, I had never used *Angular* either before this technical test. I had already some very basic notions in *React-native* but I never worked with *Angular*. It was however, quite intuitive, and I am sure I will spend more time learning about this framework in the future.

