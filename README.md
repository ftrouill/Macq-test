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

## How to run the application

You need to first have a running MongoDB. In this technical case, a local database hosted on the default port (27017) is used. To start it, run
`./run-db`

Then, you need to start the backend server. In this case, a local server running on the default port (9000) is started.
`./run-backend`


Lastly, you can run the frontend application. For this case, the local server will run on the default port (4200).
`./run-frontend`

**Remark:** If you didn't manage to start the scripts, you can simply run the commands manually.

For the database, run
`mongod`

For the backend server, move to the `backend` folder and run
`sbt run`

For the frontend applicaiton, move to the `frontend` folder and run
`ng serve`

## Limitations

Compared to the user story, there are some limitations in this implementation.

### Backend

- This implementation assumes that someone that tries to login has already an account. If the account given to the server is wrong (the combination of `username`-`password` is not recognized), an internal error occurs. I didn't manage to find how to check if a given query is empty or not. With that point, it would be easy to first check that the query is empty and to return a `BadRequest` to the client, instead of throwing an internal error.
- This implementation doesn't use JWT for the authentication. I knew the principle of JWT but I never implemented it myself. Since I had to wrap everything in a week, I didn't focus on that part.
Currently, the token for the client is the `_id` from the MongoDB corresponding to its account. It is of course not a good practice in a real application.
Using JWT, a good practice to ensure authentication would be to require the client to send a `POST` request with its `username` and `password`, as done in this project. Then, the server would generate a token based on the `user_id` (could be the username is unique, or the MongoDB object ID) using JWT, and then send it back to the client, in order to identify him for the next requests. Moreover, in order to reinforce the security, one could use the idea of access and refresh tokens. The access token would be valid for a short period, and the server would require the refresh token of the client to give a new access token when the previous one is expired.
- The user story stated that the user should be able to store a horse with a field named 'Image'. I never used MongoDB to store images and I hence considered in this implementation that 'Image' is a text field, as well as the rest of the attributes of a horse. I know that I should have use *GridFS* for example, in order to store images in the database, but I had not the time to investigate more in this week.

As a last remark on the backend part, I had never used *Scala* nor *Play! Framework* before this technical test. I am sure that I have a lot to learn in order to produce something that is clear and clean, but here is the result with less than a week.

### Frontend

For the frontend part, the base application is based on a work already done by Jason Watmore. You can find his work [here](https://jasonwatmore.com/post/2020/04/28/angular-9-user-registration-and-login-example-tutorial). I am more confortable in the backend part of the application than in the frontend part. Moreover, I also wanted to get rid of the design part of the work, which I think was less relevant than the backend and frontend part. I adapted his work to my application.

- Since the backend gives an internal error when an unknown user tries to log in, a bad alert also shows on the screen.
- Once logged in, the horses are given in a table, but the image field is just a text field, as stated before. I didn't include the feature to upload an image, knowing that on the backend part, it was only dealing with strings.

As a last remark about the frontend part, I had never used *Angular* either before this technical test. I had already some very basic notions in *React-native* but I never worked with *Angular*. It was however, quite intuitive, and I am sure I will spend more time learning about this framework in the future.

### Additional remarks

When I tried to connect the frontend with the backend server, I had some issues CORS issues. In order to still make the application work, I needed to use *Google Chrome* without the CORS 