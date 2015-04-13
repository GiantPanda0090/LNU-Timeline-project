from django.shortcuts import render

from django.views.generic import TemplateView

class CoreTemplateView(TemplateView):
    template_name = 'core/core.html'


