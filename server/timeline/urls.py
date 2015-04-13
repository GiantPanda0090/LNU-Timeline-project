
from django.conf.urls import url, include

from rest_framework.routers import DefaultRouter

from .views import TimelineViewsets, UserViewSet, EventViewSet


router = DefaultRouter()
router.register(r'timelines', TimelineViewsets)
router.register(r'users', UserViewSet)
router.register(r'events', EventViewSet)


urlpatterns = [
    url(r'^', include(router.urls)),
    url(r'api-auth/', include('rest_framework.urls', namespace='rest_framework')),
]
