1) prerequistics : node v-12
2) Angular v8

Start the app

Navigate to the UI code folder

>npm install
>ng serve

Attached Testing json files: (inside Rabo java project- src/resource)
1 - with correct record
2 - with Duplicate record
3 - with Incorrect End balance
4 - with Duplicate record and Invalid balance
Can use these files to test the output.

Verify the spring boot app running in 7070, or change the same port in angular app also.

steps to check the screen:
1) Choose the json file - it will show the preview - we can edit that before sending for validation
If the json content is not well formed, the editor will show error.
2) click "Verify Transaction Detail" for making API call
3) response will be shown in right side.

To run Unit Test case:
> ng test

