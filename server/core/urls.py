from django.conf.urls import url

from .views import CoreTemplateView, core

urlpatterns = [

    url(r'^$', core , name='core'),

]