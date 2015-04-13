from django.conf.urls import url

from .views import CoreTemplateView

urlpatterns = [

    url(r'^$', CoreTemplateView.as_view() , name='core'),

]