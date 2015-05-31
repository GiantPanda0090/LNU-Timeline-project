Install instructions for timeline-project server.
Group 2

Installation:
if you are running a *nix operating system you have all of the
basic requirments to handle the server in a development mode.

If you are running Windows, install python from:
www.python.org.

This application is developed with Python 2.7.9.

On order to download required packages, install pip.
How to install pip can be found here:
https://pip.pypa.io/en/latest/installing.html

When pip is installed.
Run the following command, you are required to run this command
in the same directory as this file is located.
sudo pip install -r requirements.txt

To start the development server.
Run the following command:
python manage.py runserver 0.0.0.0:8000

The development server is up and running.

You can now change the API config in our java client.
Enter your local IP address and port to 8000.
