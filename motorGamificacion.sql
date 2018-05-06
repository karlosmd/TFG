-- Adminer 4.6.2 PostgreSQL dump

DROP TABLE IF EXISTS "achievementcategories";
DROP SEQUENCE IF EXISTS achievementcategories_id_seq;
CREATE SEQUENCE achievementcategories_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."achievementcategories" (
    "id" integer DEFAULT nextval('achievementcategories_id_seq') NOT NULL,
    "name" character varying(255) NOT NULL,
    CONSTRAINT "pk_achievementcategories" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "achievementcategories" ("id", "name") VALUES
(4,	'Oro'),
(5,	'Plata'),
(6,	'Bronce'),
(7,	'Platino');

DROP TABLE IF EXISTS "achievementproperties";
DROP SEQUENCE IF EXISTS achievementproperties_id_seq;
CREATE SEQUENCE achievementproperties_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."achievementproperties" (
    "id" integer DEFAULT nextval('achievementproperties_id_seq') NOT NULL,
    "name" character varying(255) NOT NULL,
    "is_variable" boolean NOT NULL,
    CONSTRAINT "pk_achievementproperties" PRIMARY KEY ("id")
) WITH (oids = false);


DROP TABLE IF EXISTS "achievements";
DROP SEQUENCE IF EXISTS achievements_id_seq;
CREATE SEQUENCE achievements_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."achievements" (
    "id" integer DEFAULT nextval('achievements_id_seq') NOT NULL,
    "achievementcategory_id" integer,
    "name" character varying(255) NOT NULL,
    "maxlevel" integer NOT NULL,
    "hidden" boolean NOT NULL,
    "valid_start" date,
    "valid_end" date,
    "lat" double precision,
    "lng" double precision,
    "max_distance" integer,
    "priority" integer,
    "evaluation" evaluation_types NOT NULL,
    "evaluation_timezone" character varying,
    "relevance" relevance_types,
    "view_permission" achievement_view_permission,
    "created_at" timestamp NOT NULL,
    CONSTRAINT "pk_achievements" PRIMARY KEY ("id"),
    CONSTRAINT "fk_achievements_achievementcategory_id_achievementcategories" FOREIGN KEY (achievementcategory_id) REFERENCES achievementcategories(id) ON DELETE SET NULL NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "ix_achievements_achievementcategory_id" ON "public"."achievements" USING btree ("achievementcategory_id");

CREATE INDEX "ix_achievements_priority" ON "public"."achievements" USING btree ("priority");

INSERT INTO "achievements" ("id", "achievementcategory_id", "name", "maxlevel", "hidden", "valid_start", "valid_end", "lat", "lng", "max_distance", "priority", "evaluation", "evaluation_timezone", "relevance", "view_permission", "created_at") VALUES
(12,	7,	'Leyenda - Consigue la puntuación maxima del juego (1.000.000 pts)',	1,	'',	NULL,	NULL,	NULL,	NULL,	NULL,	1,	'immediately',	NULL,	'own',	'everyone',	'2018-04-30 13:45:54'),
(2,	4,	'Pleno - Termina un reto con el 100% de aciertos',	1,	'',	NULL,	NULL,	NULL,	NULL,	NULL,	2,	'immediately',	NULL,	'own',	'everyone',	'2018-04-19 19:11:22'),
(5,	5,	'No esta mal - Termina un reto con el 70% de aciertos',	1,	'',	NULL,	NULL,	NULL,	NULL,	NULL,	5,	'immediately',	NULL,	'own',	'everyone',	'2018-04-19 19:16:04'),
(10,	5,	'A 100 - Consigue 100 puntos en un reto',	1,	'',	NULL,	NULL,	NULL,	NULL,	NULL,	4,	'immediately',	NULL,	'own',	'everyone',	'2018-04-26 09:55:44'),
(1,	6,	'Primeros pasos - Termina un reto',	1,	'',	NULL,	NULL,	NULL,	NULL,	NULL,	6,	'immediately',	NULL,	'own',	'everyone',	'2018-04-17 15:41:46');

DROP TABLE IF EXISTS "achievements_achievementproperties";
CREATE TABLE "public"."achievements_achievementproperties" (
    "achievement_id" integer NOT NULL,
    "property_id" integer NOT NULL,
    "value" character varying(255),
    "value_translation_id" integer,
    "from_level" integer NOT NULL,
    CONSTRAINT "pk_achievements_achievementproperties" PRIMARY KEY ("achievement_id", "property_id", "from_level"),
    CONSTRAINT "fk_achievements_achievementproperties_achievement_id_achievemen" FOREIGN KEY (achievement_id) REFERENCES achievements(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_achievements_achievementproperties_property_id_achievementpr" FOREIGN KEY (property_id) REFERENCES achievementproperties(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_achievements_achievementproperties_value_translation_id_tran" FOREIGN KEY (value_translation_id) REFERENCES translationvariables(id) ON DELETE RESTRICT NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "achievements_rewards";
DROP SEQUENCE IF EXISTS achievements_rewards_id_seq;
CREATE SEQUENCE achievements_rewards_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."achievements_rewards" (
    "id" integer DEFAULT nextval('achievements_rewards_id_seq') NOT NULL,
    "achievement_id" integer NOT NULL,
    "reward_id" integer NOT NULL,
    "value" character varying(255),
    "value_translation_id" integer,
    "from_level" integer NOT NULL,
    CONSTRAINT "pk_achievements_rewards" PRIMARY KEY ("id"),
    CONSTRAINT "fk_achievements_rewards_achievement_id_achievements" FOREIGN KEY (achievement_id) REFERENCES achievements(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_achievements_rewards_reward_id_rewards" FOREIGN KEY (reward_id) REFERENCES rewards(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_achievements_rewards_value_translation_id_translationvariabl" FOREIGN KEY (value_translation_id) REFERENCES translationvariables(id) NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "ix_achievements_rewards_achievement_id" ON "public"."achievements_rewards" USING btree ("achievement_id");

CREATE INDEX "ix_achievements_rewards_from_level" ON "public"."achievements_rewards" USING btree ("from_level");

CREATE INDEX "ix_achievements_rewards_reward_id" ON "public"."achievements_rewards" USING btree ("reward_id");


DROP TABLE IF EXISTS "achievements_users";
DROP SEQUENCE IF EXISTS achievements_users_id_seq;
CREATE SEQUENCE achievements_users_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."achievements_users" (
    "id" integer DEFAULT nextval('achievements_users_id_seq') NOT NULL,
    "user_id" bigint NOT NULL,
    "achievement_id" integer NOT NULL,
    "achievement_date" timestamp,
    "level" integer NOT NULL,
    "updated_at" timestamp NOT NULL,
    CONSTRAINT "pk_achievements_users" PRIMARY KEY ("id"),
    CONSTRAINT "fk_achievements_users_achievement_id_achievements" FOREIGN KEY (achievement_id) REFERENCES achievements(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_achievements_users_user_id_users" FOREIGN KEY (user_id) REFERENCES users(id) NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "idx_achievements_users_date_not_null_unique" ON "public"."achievements_users" USING btree ("user_id", "achievement_id", "achievement_date", "level");

CREATE INDEX "idx_achievements_users_date_null_unique" ON "public"."achievements_users" USING btree ("user_id", "achievement_id", "level");

CREATE INDEX "ix_achievements_users_achievement_date" ON "public"."achievements_users" USING btree ("achievement_date");

CREATE INDEX "ix_achievements_users_achievement_id" ON "public"."achievements_users" USING btree ("achievement_id");

CREATE INDEX "ix_achievements_users_level" ON "public"."achievements_users" USING btree ("level");

CREATE INDEX "ix_achievements_users_updated_at" ON "public"."achievements_users" USING btree ("updated_at");

CREATE INDEX "ix_achievements_users_user_id" ON "public"."achievements_users" USING btree ("user_id");


DROP TABLE IF EXISTS "alembic_version";
CREATE TABLE "public"."alembic_version" (
    "version_num" character varying(32) NOT NULL,
    CONSTRAINT "alembic_version_pkc" PRIMARY KEY ("version_num")
) WITH (oids = false);

INSERT INTO "alembic_version" ("version_num") VALUES
('2012674516fc');

DROP TABLE IF EXISTS "auth_roles";
DROP SEQUENCE IF EXISTS auth_roles_id_seq;
CREATE SEQUENCE auth_roles_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."auth_roles" (
    "id" integer DEFAULT nextval('auth_roles_id_seq') NOT NULL,
    "name" character varying(100),
    CONSTRAINT "pk_auth_roles" PRIMARY KEY ("id"),
    CONSTRAINT "uq_auth_roles_name" UNIQUE ("name")
) WITH (oids = false);


DROP TABLE IF EXISTS "auth_roles_permissions";
DROP SEQUENCE IF EXISTS auth_roles_permissions_id_seq;
CREATE SEQUENCE auth_roles_permissions_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."auth_roles_permissions" (
    "id" integer DEFAULT nextval('auth_roles_permissions_id_seq') NOT NULL,
    "role_id" integer NOT NULL,
    "name" character varying(255) NOT NULL,
    CONSTRAINT "pk_auth_roles_permissions" PRIMARY KEY ("id"),
    CONSTRAINT "uq_auth_roles_permissions_role_id" UNIQUE ("role_id", "name"),
    CONSTRAINT "fk_auth_roles_permissions_role_id_auth_roles" FOREIGN KEY (role_id) REFERENCES auth_roles(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "ix_auth_roles_permissions_role_id" ON "public"."auth_roles_permissions" USING btree ("role_id");


DROP TABLE IF EXISTS "auth_tokens";
DROP SEQUENCE IF EXISTS auth_tokens_id_seq;
CREATE SEQUENCE auth_tokens_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."auth_tokens" (
    "id" bigint DEFAULT nextval('auth_tokens_id_seq') NOT NULL,
    "user_id" bigint NOT NULL,
    "token" character varying NOT NULL,
    "valid_until" timestamp NOT NULL,
    CONSTRAINT "pk_auth_tokens" PRIMARY KEY ("id"),
    CONSTRAINT "fk_auth_tokens_user_id_auth_users" FOREIGN KEY (user_id) REFERENCES auth_users(user_id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "auth_users";
CREATE TABLE "public"."auth_users" (
    "user_id" bigint NOT NULL,
    "email" character varying,
    "password_hash" character varying NOT NULL,
    "password_salt" character varying NOT NULL,
    "active" boolean NOT NULL,
    "created_at" timestamp NOT NULL,
    CONSTRAINT "pk_auth_users" PRIMARY KEY ("user_id"),
    CONSTRAINT "uq_auth_users_email" UNIQUE ("email"),
    CONSTRAINT "fk_auth_users_user_id_users" FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "auth_users_roles";
CREATE TABLE "public"."auth_users_roles" (
    "user_id" bigint NOT NULL,
    "role_id" bigint NOT NULL,
    CONSTRAINT "pk_auth_users_roles" PRIMARY KEY ("user_id", "role_id"),
    CONSTRAINT "fk_auth_users_roles_role_id_auth_roles" FOREIGN KEY (role_id) REFERENCES auth_roles(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_auth_users_roles_user_id_auth_users" FOREIGN KEY (user_id) REFERENCES auth_users(user_id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "denials";
CREATE TABLE "public"."denials" (
    "from_id" integer NOT NULL,
    "to_id" integer NOT NULL,
    CONSTRAINT "pk_denials" PRIMARY KEY ("from_id", "to_id"),
    CONSTRAINT "fk_denials_from_id_achievements" FOREIGN KEY (from_id) REFERENCES achievements(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_denials_to_id_achievements" FOREIGN KEY (to_id) REFERENCES achievements(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "goal_evaluation_cache";
DROP SEQUENCE IF EXISTS goal_evaluation_cache_id_seq;
CREATE SEQUENCE goal_evaluation_cache_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."goal_evaluation_cache" (
    "id" integer DEFAULT nextval('goal_evaluation_cache_id_seq') NOT NULL,
    "goal_id" integer NOT NULL,
    "achievement_date" timestamp,
    "user_id" bigint NOT NULL,
    "achieved" boolean,
    "value" double precision,
    CONSTRAINT "pk_goal_evaluation_cache" PRIMARY KEY ("id"),
    CONSTRAINT "fk_goal_evaluation_cache_goal_id_goals" FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_goal_evaluation_cache_user_id_users" FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "idx_goal_evaluation_cache_date_not_null_unique" ON "public"."goal_evaluation_cache" USING btree ("user_id", "goal_id", "achievement_date");

CREATE INDEX "idx_goal_evaluation_cache_date_null_unique" ON "public"."goal_evaluation_cache" USING btree ("user_id", "goal_id");

CREATE INDEX "ix_goal_evaluation_cache_goal_id" ON "public"."goal_evaluation_cache" USING btree ("goal_id");

CREATE INDEX "ix_goal_evaluation_cache_user_id" ON "public"."goal_evaluation_cache" USING btree ("user_id");


DROP TABLE IF EXISTS "goal_trigger_executions";
DROP SEQUENCE IF EXISTS goal_trigger_executions_id_seq;
CREATE SEQUENCE goal_trigger_executions_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."goal_trigger_executions" (
    "id" bigint DEFAULT nextval('goal_trigger_executions_id_seq') NOT NULL,
    "trigger_step_id" integer NOT NULL,
    "user_id" bigint NOT NULL,
    "execution_level" integer NOT NULL,
    "execution_date" timestamp NOT NULL,
    "achievement_date" timestamp,
    CONSTRAINT "pk_goal_trigger_executions" PRIMARY KEY ("id"),
    CONSTRAINT "fk_goal_trigger_executions_trigger_step_id_goal_trigger_steps" FOREIGN KEY (trigger_step_id) REFERENCES goal_trigger_steps(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_goal_trigger_executions_user_id_users" FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "ix_goal_trigger_executions_achievement_date" ON "public"."goal_trigger_executions" USING btree ("achievement_date");

CREATE INDEX "ix_goal_trigger_executions_combined" ON "public"."goal_trigger_executions" USING btree ("trigger_step_id", "user_id", "execution_level");

CREATE INDEX "ix_goal_trigger_executions_execution_date" ON "public"."goal_trigger_executions" USING btree ("execution_date");


DROP TABLE IF EXISTS "goal_trigger_steps";
DROP SEQUENCE IF EXISTS goal_trigger_steps_id_seq;
CREATE SEQUENCE goal_trigger_steps_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."goal_trigger_steps" (
    "id" integer DEFAULT nextval('goal_trigger_steps_id_seq') NOT NULL,
    "goal_trigger_id" integer NOT NULL,
    "step" integer NOT NULL,
    "condition_type" goal_trigger_condition_types,
    "condition_percentage" double precision,
    "action_type" goal_trigger_action_types,
    "action_translation_id" integer,
    CONSTRAINT "pk_goal_trigger_steps" PRIMARY KEY ("id"),
    CONSTRAINT "uq_goal_trigger_steps_goal_trigger_id" UNIQUE ("goal_trigger_id", "step"),
    CONSTRAINT "fk_goal_trigger_steps_action_translation_id_translationvariable" FOREIGN KEY (action_translation_id) REFERENCES translationvariables(id) ON DELETE RESTRICT NOT DEFERRABLE,
    CONSTRAINT "fk_goal_trigger_steps_goal_trigger_id_goal_triggers" FOREIGN KEY (goal_trigger_id) REFERENCES goal_triggers(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "ix_goal_trigger_steps_goal_trigger_id" ON "public"."goal_trigger_steps" USING btree ("goal_trigger_id");


DROP TABLE IF EXISTS "goal_triggers";
DROP SEQUENCE IF EXISTS goal_triggers_id_seq;
CREATE SEQUENCE goal_triggers_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."goal_triggers" (
    "id" integer DEFAULT nextval('goal_triggers_id_seq') NOT NULL,
    "name" character varying(100) NOT NULL,
    "goal_id" integer NOT NULL,
    "execute_when_complete" boolean DEFAULT false NOT NULL,
    CONSTRAINT "pk_goal_triggers" PRIMARY KEY ("id"),
    CONSTRAINT "fk_goal_triggers_goal_id_goals" FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "ix_goal_triggers_goal_id" ON "public"."goal_triggers" USING btree ("goal_id");


DROP TABLE IF EXISTS "goalproperties";
DROP SEQUENCE IF EXISTS goalproperties_id_seq;
CREATE SEQUENCE goalproperties_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."goalproperties" (
    "id" integer DEFAULT nextval('goalproperties_id_seq') NOT NULL,
    "name" character varying(255) NOT NULL,
    "is_variable" boolean NOT NULL,
    CONSTRAINT "pk_goalproperties" PRIMARY KEY ("id")
) WITH (oids = false);


DROP TABLE IF EXISTS "goals";
DROP SEQUENCE IF EXISTS goals_id_seq;
CREATE SEQUENCE goals_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."goals" (
    "id" integer DEFAULT nextval('goals_id_seq') NOT NULL,
    "name" character varying(255) NOT NULL,
    "name_translation_id" integer,
    "condition" character varying(255),
    "timespan" integer,
    "group_by_key" boolean,
    "group_by_dateformat" character varying(255),
    "goal" character varying(255),
    "operator" goal_operators,
    "maxmin" goal_maxmin,
    "achievement_id" integer NOT NULL,
    "priority" integer,
    CONSTRAINT "pk_goals" PRIMARY KEY ("id"),
    CONSTRAINT "fk_goals_achievement_id_achievements" FOREIGN KEY (achievement_id) REFERENCES achievements(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_goals_name_translation_id_translationvariables" FOREIGN KEY (name_translation_id) REFERENCES translationvariables(id) ON DELETE RESTRICT NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "ix_goals_priority" ON "public"."goals" USING btree ("priority");

INSERT INTO "goals" ("id", "name", "name_translation_id", "condition", "timespan", "group_by_key", "group_by_dateformat", "goal", "operator", "maxmin", "achievement_id", "priority") VALUES
(4,	'100%aciertos',	NULL,	'{ "term": { "type": "literal", "variable": "porcentajeAciertos" } }',	NULL,	'',	NULL,	'100',	'geq',	'max',	2,	'0'),
(5,	'70%aciertos',	NULL,	'{ "term": { "type": "literal", "variable": "porcentajeAciertos" } }',	NULL,	'',	NULL,	'70',	'geq',	'max',	5,	'0'),
(8,	'100puntos',	NULL,	'{ "term": { "type": "literal", "variable": "puntuacion" } }',	NULL,	'',	NULL,	'100',	'geq',	'max',	10,	'0'),
(3,	'terminar reto',	NULL,	'{ "term": { "type": "literal", "variable": "tiempo" } } ',	NULL,	'',	NULL,	'10',	'geq',	'max',	1,	'0'),
(10,	'PuntuacionMaxima',	NULL,	'{ "term": { "type": "literal", "variable": "puntuacion" } } ',	NULL,	'',	NULL,	'1000000',	'geq',	'max',	12,	'0');

DROP TABLE IF EXISTS "goals_goalproperties";
CREATE TABLE "public"."goals_goalproperties" (
    "goal_id" integer NOT NULL,
    "property_id" integer NOT NULL,
    "value" character varying(255),
    "value_translation_id" integer,
    "from_level" integer NOT NULL,
    CONSTRAINT "pk_goals_goalproperties" PRIMARY KEY ("goal_id", "property_id", "from_level"),
    CONSTRAINT "fk_goals_goalproperties_goal_id_goals" FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_goals_goalproperties_property_id_goalproperties" FOREIGN KEY (property_id) REFERENCES goalproperties(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_goals_goalproperties_value_translation_id_translationvariabl" FOREIGN KEY (value_translation_id) REFERENCES translationvariables(id) ON DELETE RESTRICT NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "groups";
DROP SEQUENCE IF EXISTS groups_id_seq;
CREATE SEQUENCE groups_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."groups" (
    "id" bigint DEFAULT nextval('groups_id_seq') NOT NULL,
    CONSTRAINT "pk_groups" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "groups" ("id") VALUES
(1),
(2);

DROP TABLE IF EXISTS "languages";
DROP SEQUENCE IF EXISTS languages_id_seq;
CREATE SEQUENCE languages_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."languages" (
    "id" integer DEFAULT nextval('languages_id_seq') NOT NULL,
    "name" character varying(255) NOT NULL,
    CONSTRAINT "pk_languages" PRIMARY KEY ("id")
) WITH (oids = false);

CREATE INDEX "ix_languages_name" ON "public"."languages" USING btree ("name");


DROP TABLE IF EXISTS "requirements";
CREATE TABLE "public"."requirements" (
    "from_id" integer NOT NULL,
    "to_id" integer NOT NULL,
    CONSTRAINT "pk_requirements" PRIMARY KEY ("from_id", "to_id"),
    CONSTRAINT "fk_requirements_from_id_achievements" FOREIGN KEY (from_id) REFERENCES achievements(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_requirements_to_id_achievements" FOREIGN KEY (to_id) REFERENCES achievements(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "rewards";
DROP SEQUENCE IF EXISTS rewards_id_seq;
CREATE SEQUENCE rewards_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."rewards" (
    "id" integer DEFAULT nextval('rewards_id_seq') NOT NULL,
    "name" character varying(255) NOT NULL,
    CONSTRAINT "pk_rewards" PRIMARY KEY ("id")
) WITH (oids = false);


DROP TABLE IF EXISTS "translations";
DROP SEQUENCE IF EXISTS translations_id_seq;
CREATE SEQUENCE translations_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."translations" (
    "id" integer DEFAULT nextval('translations_id_seq') NOT NULL,
    "translationvariable_id" integer NOT NULL,
    "language_id" integer NOT NULL,
    "text" text NOT NULL,
    CONSTRAINT "pk_translations" PRIMARY KEY ("id"),
    CONSTRAINT "fk_translations_language_id_languages" FOREIGN KEY (language_id) REFERENCES languages(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_translations_translationvariable_id_translationvariables" FOREIGN KEY (translationvariable_id) REFERENCES translationvariables(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "translationvariables";
DROP SEQUENCE IF EXISTS translationvariables_id_seq;
CREATE SEQUENCE translationvariables_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."translationvariables" (
    "id" integer DEFAULT nextval('translationvariables_id_seq') NOT NULL,
    "name" character varying(255) NOT NULL,
    CONSTRAINT "pk_translationvariables" PRIMARY KEY ("id")
) WITH (oids = false);

CREATE INDEX "ix_translationvariables_name" ON "public"."translationvariables" USING btree ("name");


DROP TABLE IF EXISTS "user_devices";
CREATE TABLE "public"."user_devices" (
    "device_id" character varying(255) NOT NULL,
    "user_id" bigint NOT NULL,
    "device_os" character varying NOT NULL,
    "push_id" character varying(255) NOT NULL,
    "app_version" character varying(255) NOT NULL,
    "registered_at" timestamp NOT NULL,
    CONSTRAINT "pk_user_devices" PRIMARY KEY ("device_id", "user_id"),
    CONSTRAINT "fk_user_devices_user_id_users" FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "user_messages";
DROP SEQUENCE IF EXISTS user_messages_id_seq;
CREATE SEQUENCE user_messages_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."user_messages" (
    "id" bigint DEFAULT nextval('user_messages_id_seq') NOT NULL,
    "user_id" bigint NOT NULL,
    "translation_id" integer,
    "params" json,
    "is_read" boolean NOT NULL,
    "has_been_pushed" boolean DEFAULT false NOT NULL,
    "created_at" timestamp NOT NULL,
    CONSTRAINT "pk_user_messages" PRIMARY KEY ("id"),
    CONSTRAINT "fk_user_messages_translation_id_translationvariables" FOREIGN KEY (translation_id) REFERENCES translationvariables(id) ON DELETE RESTRICT NOT DEFERRABLE,
    CONSTRAINT "fk_user_messages_user_id_users" FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);

CREATE INDEX "ix_user_messages_created_at" ON "public"."user_messages" USING btree ("created_at");

CREATE INDEX "ix_user_messages_has_been_pushed" ON "public"."user_messages" USING btree ("has_been_pushed");

CREATE INDEX "ix_user_messages_is_read" ON "public"."user_messages" USING btree ("is_read");

CREATE INDEX "ix_user_messages_user_id" ON "public"."user_messages" USING btree ("user_id");


DROP TABLE IF EXISTS "users";
DROP SEQUENCE IF EXISTS users_id_seq;
CREATE SEQUENCE users_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."users" (
    "id" bigint DEFAULT nextval('users_id_seq') NOT NULL,
    "lat" double precision,
    "lng" double precision,
    "language_id" integer,
    "timezone" character varying NOT NULL,
    "country" character varying,
    "region" character varying,
    "city" character varying,
    "additional_public_data" json,
    "created_at" timestamp NOT NULL,
    CONSTRAINT "pk_users" PRIMARY KEY ("id"),
    CONSTRAINT "fk_users_language_id_languages" FOREIGN KEY (language_id) REFERENCES languages(id) NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "users_groups";
CREATE TABLE "public"."users_groups" (
    "user_id" bigint NOT NULL,
    "group_id" bigint NOT NULL,
    CONSTRAINT "pk_users_groups" PRIMARY KEY ("user_id", "group_id"),
    CONSTRAINT "fk_users_groups_group_id_groups" FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_users_groups_user_id_users" FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "users_users";
CREATE TABLE "public"."users_users" (
    "from_id" bigint NOT NULL,
    "to_id" bigint NOT NULL,
    CONSTRAINT "pk_users_users" PRIMARY KEY ("from_id", "to_id"),
    CONSTRAINT "fk_users_users_from_id_users" FOREIGN KEY (from_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_users_users_to_id_users" FOREIGN KEY (to_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "values";
CREATE TABLE "public"."values" (
    "user_id" bigint NOT NULL,
    "datetime" timestamptz NOT NULL,
    "variable_id" integer NOT NULL,
    "value" integer NOT NULL,
    "key" character varying(100) NOT NULL,
    CONSTRAINT "pk_values" PRIMARY KEY ("user_id", "datetime", "variable_id", "key"),
    CONSTRAINT "fk_values_user_id_users" FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE NOT DEFERRABLE,
    CONSTRAINT "fk_values_variable_id_variables" FOREIGN KEY (variable_id) REFERENCES variables(id) ON DELETE CASCADE NOT DEFERRABLE
) WITH (oids = false);


DROP TABLE IF EXISTS "variables";
DROP SEQUENCE IF EXISTS variables_id_seq;
CREATE SEQUENCE variables_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;

CREATE TABLE "public"."variables" (
    "id" integer DEFAULT nextval('variables_id_seq') NOT NULL,
    "name" character varying(255) NOT NULL,
    "group" variable_group_types NOT NULL,
    "increase_permission" variable_increase_permission,
    CONSTRAINT "pk_variables" PRIMARY KEY ("id")
) WITH (oids = false);

CREATE INDEX "ix_variables_name" ON "public"."variables" USING btree ("name");

INSERT INTO "variables" ("id", "name", "group", "increase_permission") VALUES
(4,	'porcentajeAciertos',	'none',	'admin'),
(1,	'puntuacion',	'none',	'admin'),
(3,	'tiempo',	'none',	'admin');

-- 2018-05-06 22:36:59.698082+00
