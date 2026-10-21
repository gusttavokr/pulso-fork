import { Routes } from '@angular/router';
import { LoginPage } from './features/autenticacao/pages/login/login';
import { Cadastro } from './features/cadastro/pages/cadastro/cadastro';
import { CadastroEtapa1Component } from './features/cadastro/steps/etapa1/cadastro-etapa1';
import { CadastroEtapa2Component } from './features/cadastro/steps/etapa2/cadastro-etapa2';
import { CadastroEtapa3Component } from './features/cadastro/steps/etapa3/cadastro-etapa3';
import { autenticacaoGuard } from './features/autenticacao/guards/auth';
import { HomeComponent } from './features/home/pages/home-page/home-page';
import { GoogleCallbackComponent } from './layout/components/sidebars/sidebar-calendario/pages/google-callback/google-callback';
import { MeusPlantoesComponent } from './features/plantoes/pages/meus-plantoes/meus-plantoes';
import { PlantoesAbertosComponent } from './features/plantoes/pages/plantoes-abertos/plantoes-abertos';
import { MinhasEscalasComponent } from './features/escalas/pages/minhas-escalas/minhas-escalas';
import { VerEscalaComponent } from './features/escalas/pages/ver-escala/ver-escala';
import { FormPlantaoComponent } from './features/plantoes/pages/form-plantao/form-plantao';
import { CriarPlantaoGuard } from './features/plantoes/guards/criar-plantao';
import { AtualizarPlantaoGuard } from './features/plantoes/guards/atualizar-plantao';
import { VerPlantaoComponent } from './features/plantoes/pages/ver-plantao/ver-plantao';

export const routes: Routes = [
    {
        path: '', redirectTo: 'home', pathMatch: 'full'
    },

    {
        path: 'home', component: HomeComponent, canActivate: [autenticacaoGuard]
    },

    {
        path: 'login', component: LoginPage
    },

    {
        path: 'cadastro',
        component: Cadastro,
        children: [
            { path: '', pathMatch: 'full', redirectTo: 'etapa1' },
            { path: 'etapa1', component: CadastroEtapa1Component },
            { path: 'etapa2', component: CadastroEtapa2Component },
            { path: 'etapa3', component: CadastroEtapa3Component },
        ]
    },

    {
        path: 'meus-plantoes', component: MeusPlantoesComponent, canActivate: [autenticacaoGuard]
    },

    {
        path: 'plantoes-abertos', component: PlantoesAbertosComponent, canActivate: [autenticacaoGuard]

    },

    { 
        path: 'plantao/criar', component: FormPlantaoComponent, canActivate: [autenticacaoGuard, CriarPlantaoGuard] 
    },

    {
        path: 'plantao/:id/atualizar', component: FormPlantaoComponent, canActivate: [autenticacaoGuard, AtualizarPlantaoGuard]

    },

    {
        path: 'plantao/:id', component: VerPlantaoComponent, canActivate: [autenticacaoGuard] 
    },

    {
        path: 'minhas-escalas', component: MinhasEscalasComponent, canActivate: [autenticacaoGuard]
    },

    {
        path: 'escala/:id', component: VerEscalaComponent, canActivate: [autenticacaoGuard]
    },

    {
        path: 'google-callback', component: GoogleCallbackComponent
    },

    {
        path: '**', redirectTo: 'home'
    },
];
