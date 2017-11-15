# Wit.ai-E-Portfolio
All files for my E-Portfolio of the Software Engineering lecture can be found here.

## Slides

The slides can be found in the folder [Documentation](documentation).

## Cheat Sheet
Take a look at wit.ai's documentation:

 - [Recipes](https://wit.ai/docs/recipes)

 - [HTTP API](https://wit.ai/docs/http/20170307)
 
## Tutorial

This tutorial will guide you through the process of creating an wit.ai app and using java to query it.

### Step 1 - Log in

Head to [wit.ai](https://wit.ai/) and log in with your GitHub account.
Follow the instructions until you see the welcome page of your app.

### Step 2 - Create your app

![create_app](/documentation/img/create_app.png)

### Step 3 - Detect you first entity

Wit allows you to understand what your users say to your app.

In our example, they could say:

    What’s the current temperature?
    Is it me or it is hot here?
    I want to set the temperature to 71 degrees

All these examples are about getting or setting the temperature.

You will configure Wit by example. You don’t need to describe every way a user might ask the temperature, Wit will infer this from the few examples you give. However, keep in mind that the more examples you give, the better your app will understand your users. Type an example of someone asking the temperature in the “User says…” box. For example: *what's the temperature inside?*

You want to teach Wit that this sentence as a whole is about getting the office temperature. To do so, you will use a trait entity. An entity is a piece of information you would want to detect from a user input. There are different types of entity but we will see that later. To do so, you will use a trait entity (you can read more about intents and different types of entities)

For now, just type ‘intent’ in the “Add a new entity” field and select the default intent entity. Then just type the name of your new intent. For example temperature_get.

Then click *Validate*.

![detect_first_entity](/documentation/img/detect_first_entity.gif)

### Step 4 - Improve detection

Your app doesn’t know a lot yet, but it will start to recognize the intent to get the actual temperature of your device.

You can try a few more examples of asking about the temperature. For example *What's the current temperature?* and *Is it me or it is hot here?*. Each time, validate the example with the intent entity **temperature_get**

The more you validate examples, the better Wit will understand.

![improve_detection](/documentation/img/improve_detection.gif)

### Step 5 - Query your app

Set the access-token in the _eportfolio.straub.main.GUI.java_ file to your apps access token. It can be found in the **settings**-tab of your app.

![token](/documentation/img/token.PNG)

Running the __main method__ from _eportfolio.straub.main.GUI.java_ to launch the text and speech querying tool.

![querying_gui](/documentation/img/querying_gui.PNG)

### Step 6 - Add a new entity value

Go in the Inbox tab. Surprise… You should see the examples you tried when curl-ing the API

You can validate the *what's the temperature in there?*` as the intent was correctly captured by Wit.

Now take the *set the temperature to 70 degrees* example. Type a new value for the existing intent entity, for example **temperature_set**. You could also have done this from the Understanding tab by repeating steps 3 and 4.

*Don’t validate yet and wait until step 7.*

![add_new_entity](/documentation/img/add_new_entity.gif)

### Step 7 - Capture more

In the previous example, we also want to capture the targeted temperature. Click on the **“Add a new entity”** field. You can create your own one or select a common one from the dropdown list. Find and select *wit/temperature*.

Wit may highlight the relevant information in your example automatically. If not, you would highlight the part of the example that corresponds to the additional information you want to capture. This is call a free-text entity. Here we highlighted *70 degrees*. Then click Validate.

You can now do the same for the following example *turn the temperature to 62 degrees*. Go back to the Understanding tab and type this example in the “User says…” box. Make sure the Intent is **temperature_set**. Then you would select the entity *wit/temperature* and highlight *62 degrees*.

![capture_more](/documentation/img/capture_more.gif)

See [quickstart](https://wit.ai/docs/quickstart) at wit.ai 




