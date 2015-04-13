from django.conf.urls import include, url
from django.contrib import admin

from rest_framework.authtoken import views

urlpatterns = [
    # Examples:
    # url(r'^$', 'settings.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    # Timeline
    #url(r'^api/v1/', include(admin.site.urls)),
    
    # Admin site
    url(r'^admin/', include(admin.site.urls)),

    # Rest auth
    url(r'^rest-auth/', include('rest_auth.urls')),
    url(r'^rest-auth/registration/', include('rest_auth.registration.urls')),

    # Allauth
    url(r'^account/', include('allauth.urls')),
]

urlpatterns += [
    url(r'^api-token-auth/', views.obtain_auth_token)
]
