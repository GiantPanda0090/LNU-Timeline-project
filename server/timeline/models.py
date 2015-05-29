from django.db import models

from django.contrib.auth.models import User

class TimeStampedModel(models.Model):
    '''
    This is a abstract class to keep track on then a model was created and when its updated
    '''
    created = models.DateTimeField(auto_now_add=True)
    modified = models.DateTimeField(auto_now=True)

    class Meta:
        abstract = True
        get_latest_by = 'modified'
        ordering = ('-modified', '-created')


class Timeline(TimeStampedModel):
    '''
    Timeline model
    '''
    timeline_title = models.CharField(max_length=254)
    timeline_description = models.TextField(default='description', blank=True)
    timeline_start_datetime = models.DateTimeField(null=True, blank=True)
    timeline_stop_datetime = models.DateTimeField(null=True, blank=True)
    user = models.ForeignKey(User, null=True, blank=True)
    
    def __unicode__(self):
        return self.timeline_title

class Event(TimeStampedModel):
    '''
    Event model
    '''
    event_title = models.CharField(max_length = 254)
    event_description = models.TextField()
    event_start_datetime = models.DateTimeField()
    event_stop_datetime = models.DateTimeField()
    timeline = models.ForeignKey(Timeline)

    def __unicode__(self):
        return self.event_title








