create or replace function random_bootKlasse(length integer) returns text as 
$$
declare
  chars text[] := '{A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z}';
  result text := '';
  i integer := 0;
begin
  if length < 0 then
    raise exception 'Given length cannot be less than 0';
  end if;
  for i in 1..length loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)];
  end loop;
  return result;
end;
$$ language plpgsql;

 CREATE OR REPLACE FUNCTION upsert(sql_insert text, sql_update text)

 RETURNS void AS
 $BODY$
 BEGIN
    -- first try to insert and after to update. Note : insert has pk and update not...

    EXECUTE sql_insert;
    RETURN;
    EXCEPTION WHEN unique_violation THEN
    EXECUTE sql_update; 
    IF FOUND THEN 
        RETURN; 
    END IF;
 END;
 $BODY$
 LANGUAGE plpgsql VOLATILE
 COST 100;
 ALTER FUNCTION upsert(text, text)
 OWNER TO postgres;