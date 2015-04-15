from django.shortcuts import render

from django.views.generic import TemplateView

from github.models import Repo, Contributor

class CoreTemplateView(TemplateView):
    template_name = 'core/core.html'


def core(request):
    repo = Repo.objects.all()
    cont = Contributor.objects.all()

    context = {'repo':repo, 'contributors':cont}

    return render(request, 'core/core.html', context)


