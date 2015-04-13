
from django.contrib.auth.models import User

from rest_framework import serializers

from .models import Timeline, Event

class TimelineSerializer(serializers.ModelSerializer):

    class Meta:
        model = Timeline
        fields = ('url', 'id', 'user', 'timeline_title', 'timeline_description', 'timeline_start_datetime', 'timeline_stop_datetime', 'created', 'modified')


class UserSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = User
        fields = ('url', 'id', 'username', 'email', 'first_name', 'last_name', 'date_joined', 'last_login')

class EventSerializer(serializers.ModelSerializer):

    class Meta:
        model = Event


