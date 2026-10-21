import http from "k6/http";
import { check, group, sleep } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://host.docker.internal:8080";

export const options = {
    stages: [
        { duration: "30s", target: 1000 },
        { duration: "1m", target: 1000 },
        { duration: "30s", target: 0 },
    ],

    thresholds: {
        http_req_failed: ["rate<0.01"],
        http_req_duration: [
            "avg<500",
            "p(95)<1000",
            "p(99)<2000"
        ],
    },
};

function gerarEmailUnico() {
    return `medico_${__VU}_${__ITER}_${Date.now()}@pulso.dev`;
}

function gerarMesFuturo() {
    const data = new Date();

    const deslocamento = (__VU - 1) * 1000 + __ITER;

    data.setMonth(data.getMonth() + deslocamento + 1);
    data.setDate(1);

    return data.toISOString().split("T")[0];
}

export default function () {

    const email = gerarEmailUnico();
    const senha = "senha123";

    let tokenLider;
    let escalaId;

    group("Cadastro de usuário", () => {

        const payload = {
            email,
            senha,
            profissional: {
                nome: `Medico Teste ${__VU}_${__ITER}`,
                emails: [email],
                telefone: "+55 11 99999-9999",
                fotoPerfil: null,
                anoFormacao: "2015-12-01",
                pix: email,
                tipoPix: "EMAIL",
                crmNumero: `${10000 + __VU + __ITER}`,
                crms: ["SP"],
                dataNasc: "1990-01-01",
                especialidades: ["CLINICA_GERAL"]
            }
        };

        const response = http.post(
            `${BASE_URL}/api/v1/usuarios/cadastro`,
            JSON.stringify(payload),
            {
                headers: {
                    "Content-Type": "application/json"
                }
            }
        );

        check(response, {
            "cadastro sucesso": r =>
                r.status === 200 ||
                r.status === 201
        });
    });

    group("Login líder", () => {

        const response = http.post(
            `${BASE_URL}/api/v1/usuarios/login`,
            JSON.stringify({
                email: "lider1@pulso.dev",
                senha: "senha123"
            }),
            {
                headers: {
                    "Content-Type": "application/json"
                }
            }
        );

        check(response, {
            "login líder sucesso": r => r.status === 200,
            "token líder recebido": r => !!r.json("token")
        });

        tokenLider = response.json("token");
    });

    group("Criar escala", () => {

        const payload = {
            nome: `Escala Teste ${__VU}-${__ITER}`,
            mes: gerarMesFuturo(),
            copia: false,
            setorId: 10
        };

        const response = http.post(
            `${BASE_URL}/api/v1/escalas`,
            JSON.stringify(payload),
            {
                headers: {
                    Authorization: `Bearer ${tokenLider}`,
                    "Content-Type": "application/json"
                }
            }
        );

        check(response, {
            "escala criada": r =>
                r.status === 200 ||
                r.status === 201
        });

        escalaId = response.json("id");
    });

    group("Detalhar escala", () => {

        const response = http.get(
            `${BASE_URL}/api/v1/escalas/${escalaId}`,
            {
                headers: {
                    Authorization: `Bearer ${tokenLider}`
                }
            }
        );

        check(response, {
            "detalhar escala sucesso": r => r.status === 200
        });
    });

    group("Listar escalas", () => {

        const response = http.get(
            `${BASE_URL}/api/v1/escalas?liderEscala=true`,
            {
                headers: {
                    Authorization: `Bearer ${tokenLider}`
                }
            }
        );

        check(response, {
            "listar escalas sucesso": r => r.status === 200,
            "retornou dados": r => r.body.length > 0
        });
    });

    sleep(1);
}