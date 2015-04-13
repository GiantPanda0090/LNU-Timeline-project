from django.shortcuts import render
from django.contrib.auth.models import User

from rest_framework import viewsets

from .models import Timeline, Event
from .serializers import TimelineSerializer, UserSerializer, EventSerializer

class TimelineViewsets(viewsets.ModelViewSet):
    queryset = Timeline.objects.all()
    serializer_class = TimelineSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = UserSerializer

class EventViewSet(viewsets.ModelViewSet):
    queryset = Event.objects.all()
    serializer_class = EventSerializer
