--
-- PostgreSQL database dump
--

-- Dumped from database version 11.4
-- Dumped by pg_dump version 11.2

-- Started on 2019-09-18 12:29:41

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 16644)
-- Name: user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."user" (
    email character varying NOT NULL,
    firstname character varying,
    lastname character varying,
    password character varying
);


--
-- TOC entry 2807 (class 0 OID 16644)
-- Dependencies: 196
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public."user" (email, firstname, lastname, password) FROM stdin;
\.


--
-- TOC entry 2685 (class 2606 OID 16651)
-- Name: user user_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pk PRIMARY KEY (email);


-- Completed on 2019-09-18 12:29:41

--
-- PostgreSQL database dump complete
--

